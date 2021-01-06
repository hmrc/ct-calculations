/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.box

import java.util.regex.Pattern

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.ct600.v3.retriever.RepaymentsBoxRetriever
import uk.gov.hmrc.ct.domain.ValidationConstants._
import uk.gov.hmrc.ct.utils.DateImplicits._

trait ValidatableBox[T <: BoxRetriever] extends Validators with ExtraValidation {

  // Taken from PostCodeType on http://www.hmrc.gov.uk/schemas/core-v2-0.xsd

  def validate(boxRetriever: T): Set[CtValidation]

  val validationSuccess: Set[CtValidation] = Set.empty

  protected def validateBooleanAsMandatory(boxId: String, box: OptionalBooleanIdBox)(): Set[CtValidation] = {
    box.value match {
      case None => Set(CtValidation(Some(boxId), s"error.$boxId.required"))
      case _ => validationSuccess
    }
  }

  protected def validateBooleanAsTrue(boxId: String, box: OptionalBooleanIdBox)(): Set[CtValidation] = {
    box.value match {
      case None | Some(false) => Set(CtValidation(Some(boxId), s"error.$boxId.required"))
      case _ => validationSuccess
    }
  }

  protected def validateIntegerAsMandatory(boxId: String, box: OptionalIntIdBox)(): Set[CtValidation] = {
    box.value match {
      case None => Set(CtValidation(Some(boxId), s"error.$boxId.required"))
      case _ => validationSuccess
    }
  }

  protected def validateStringAsMandatory(boxId: String, box: OptionalStringIdBox)(): Set[CtValidation] = {
    box.value match {
      case None => Set(CtValidation(Some(boxId), s"error.$boxId.required"))
      case Some(x) if x.isEmpty => Set(CtValidation(Some(boxId), s"error.$boxId.required"))
      case _ => validationSuccess
    }
  }

  protected def validateStringAsMandatoryWithNoTrailingWhitespace(boxId: String, box: OptionalStringIdBox)(): Set[CtValidation] = {
    box.value match {
      case None => Set(CtValidation(Some(boxId), s"error.$boxId.required"))
      case Some(x) if x.trim.isEmpty => Set(CtValidation(Some(boxId), s"error.$boxId.required"))
      case _ => validationSuccess

    }
  }

  protected def validateAsMandatory[U](box: CtValue[U] with CtBoxIdentifier)(): Set[CtValidation] = {
    box.value match {
      case None => Set(CtValidation(Some(box.id), s"error.${box.id}.required"))
      case Some(x:String) if x.isEmpty => Set(CtValidation(Some(boxId), s"error.$boxId.required"))
      case _ => validationSuccess
    }
  }

  protected def validateStringAsMandatoryIfPAYEEQ1False(boxRetriever: RepaymentsBoxRetriever, boxId: String, box: OptionalStringIdBox)(): Set[CtValidation] = {
    val payeeq1 = boxRetriever.payeeQ1()
    if (!payeeq1.value.getOrElse(true)) {
      validateStringAsMandatory(boxId, box)
    } else validationSuccess
  }

  protected def validateAllFilledOrEmptyStrings(boxId: String, allBoxes: Set[CtValue[String]])(): Set[CtValidation] = {
    val allEmpty = allBoxes.count(_.value.isEmpty) == allBoxes.size
    val allNonEmpty = allBoxes.count(_.value.nonEmpty) == allBoxes.size

    passIf(allEmpty || allNonEmpty) {
      Set(CtValidation(Some(boxId), s"error.$boxId.allornone"))
    }
  }

  protected def validateAllFilledOrEmptyStringsForBankDetails(boxRetriever: RepaymentsBoxRetriever, boxId: String)(): Set[CtValidation] = {
    val bankDetailsBoxGroup:Set[CtValue[String]] = Set(
      boxRetriever.b920(),
      boxRetriever.b925(),
      boxRetriever.b930(),
      boxRetriever.b935()
    )
    validateAllFilledOrEmptyStrings(boxId, bankDetailsBoxGroup)
  }

  protected def validateStringAsBlank(boxId: String, box: OptionalStringIdBox)(): Set[CtValidation] = {
    box.value match {
      case None => validationSuccess
      case Some(x) if x.isEmpty => validationSuccess
      case _ => Set(CtValidation(Some(boxId), s"error.$boxId.nonBlankValue"))
    }
  }

  protected def validateDateAsMandatory(boxId: String, box: OptionalDateIdBox)(): Set[CtValidation] = {
    validateDateAsMandatory(boxId, box.value, boxId): Set[CtValidation]
  }

  protected def validateDateAsMandatory(boxId: String, date: Option[LocalDate], messageId: String)(): Set[CtValidation] = {
    date match {
      case None => Set(CtValidation(Some(boxId), s"error.$messageId.required"))
      case _ => validationSuccess
    }
  }

  protected def validateDateAsBlank(boxId: String, box: OptionalDateIdBox)(): Set[CtValidation] = {
    box.value match {
      case None => validationSuccess
      case _ => Set(CtValidation(Some(boxId), s"error.$boxId.nonBlankValue"))
    }
  }

  protected def validateDateAsBefore(boxId: String, box: OptionalDateIdBox, dateToCompare: LocalDate)(): Set[CtValidation] = {
    box.value match {
      case None => validationSuccess
      case Some(date) if date < dateToCompare => validationSuccess
      case _ => Set(CtValidation(Some(boxId), s"error.$boxId.not.before"))
    }
  }

  protected def validateDateAsAfter(boxId: String, box: OptionalDateIdBox, dateToCompare: LocalDate)(): Set[CtValidation] = {
    box.value match {
      case None => validationSuccess
      case Some(date) if date > dateToCompare => validationSuccess
      case _ => Set(CtValidation(Some(boxId), s"error.$boxId.not.after"))
    }
  }

  protected def validateDateAsBetweenInclusive(boxId: String, box: OptionalDateIdBox, minDate: LocalDate, maxDate: LocalDate)(): Set[CtValidation] = {
    validateDateAsBetweenInclusive(boxId, box.value, minDate, maxDate, boxId)
  }

  protected def validateDateAsBetweenInclusive(boxId: String, date: Option[LocalDate], minDate: LocalDate, maxDate: LocalDate, messageId: String)(): Set[CtValidation] = {
    date match {
      case None => validationSuccess
      case Some(d) if d < minDate.toDateTimeAtStartOfDay.toLocalDate || d > maxDate.plusDays(1).toDateTimeAtStartOfDay.minusSeconds(1).toLocalDate =>
        Set(CtValidation(Some(boxId), s"error.$messageId.not.betweenInclusive", Some(Seq(toErrorArgsFormat(minDate), toErrorArgsFormat(maxDate)))))
      case _ => validationSuccess
    }
  }

  protected def validateIntegerAsBlank(boxId: String, box: OptionalIntIdBox)(): Set[CtValidation] = {
    box.value match {
      case None => validationSuccess
      case _ => Set(CtValidation(Some(boxId), s"error.$boxId.nonBlankValue"))
    }
  }

  protected def validateIntegerRange(boxId: String, box: OptionalIntIdBox, min: Int, max: Int)(): Set[CtValidation] = {
    box.value match {
      case Some(x) => {
        passIf (min <= x && x <= max) {
           Set(CtValidation(Some(boxId), s"error.$boxId.outOfRange", Some(Seq(commaForThousands(min), commaForThousands(max)))))
        }
      }
      case _ => validationSuccess
    }
  }

  protected def validateZeroOrPositiveBigDecimal(box: OptionalBigDecimalIdBox)(): Set[CtValidation] = {
    box.value match {
      case Some(x) if x < BigDecimal(0) => Set(CtValidation(Some(box.id), s"error.${box.id}.mustBeZeroOrPositive"))
      case _ => validationSuccess
    }
  }

  protected def validateZeroOrPositiveInteger(box: OptionalIntIdBox)(): Set[CtValidation] = {
    box.value match {
      case Some(x) if x < 0 => Set(CtValidation(Some(box.id), s"error.${box.id}.mustBeZeroOrPositive"))
      case _ => validationSuccess
    }
  }

  protected def validateZeroOrNegativeInteger(box: OptionalIntIdBox)(): Set[CtValidation] = {
    box.value match {
      case Some(x) if x > 0 => Set(CtValidation(Some(box.id), s"error.${box.id}.mustBeNegativeOrZero"))
      case _ => validationSuccess
    }
  }

  protected def validatePositiveBigDecimal(box: OptionalBigDecimalIdBox)(): Set[CtValidation] = {
    box.value match {
      case Some(x) if x <= 0 => Set(CtValidation(Some(box.id), s"error.${box.id}.mustBePositive"))
      case _ => validationSuccess
    }
  }

  protected def validatePositiveInteger(box: OptionalIntIdBox)(): Set[CtValidation] = {
    box.value match {
      case Some(x) if x <= 0 => Set(CtValidation(Some(box.id), s"error.${box.id}.mustBePositive"))
      case _ => validationSuccess
    }
  }

  protected def validateOptionalIntegerAsEqualTo(box: CtBoxIdentifier with CtOptionalInteger, equalToBox: CtBoxIdentifier with CtOptionalInteger): Set[CtValidation] = {
    if (box.orZero != equalToBox.orZero)
      Set(CtValidation(Some(box.id), s"error.${box.id}.mustEqual.${equalToBox.id}"))
    else
      Set.empty
  }

  protected def validateOptionalStringByRegex(boxId: String, box: OptionalStringIdBox, regex: String)(): Set[CtValidation] = {
    box.value match {
      case Some(x) if x.nonEmpty => {
        passIf (x.matches(regex)) {
          Set(CtValidation(Some(boxId), s"error.$boxId.regexFailure"))
        }
      }
      case _ => validationSuccess
    }
  }

  protected def validateRawStringByRegex(boxId: String, value: String, errorCodeBoxId: String, regex: String)(): Set[CtValidation] = {
    passIf (value.matches(regex)) {
      Set(CtValidation(Some(boxId), s"error.$errorCodeBoxId.regexFailure"))
    }
  }

  protected def validateStringByRegex(boxId: String, box: StringIdBox, regex: String)(): Set[CtValidation] = {
    passIf (box.value.isEmpty || box.value.matches(regex)) {
      Set(CtValidation(Some(boxId), s"error.$boxId.regexFailure"))
    }
  }

  protected def validateCoHoStringReturnIllegalChars(boxId: String, box: OptionalStringIdBox)(): Set[CtValidation] = {
    box.value match {
      case Some(x) if x.nonEmpty => {
        validateCoHoStringReturnIllegalChars(boxId, x)
      }
      case _ => validationSuccess
    }
  }

  protected def validateCohoNameField(boxId: String, box: StringIdBox)(): Set[CtValidation] = {
    validateStringByRegex(boxId, box, ValidCoHoNamesCharacters)
  }

  protected def validateCohoOptionalNameField(boxId: String, box: OptionalStringIdBox)(): Set[CtValidation] = {
    validateOptionalStringByRegex(boxId, box, ValidCoHoNamesCharacters)
  }

  protected def validateCoHoStringReturnIllegalChars(boxId: String, value: String, errorCodeBoxId: Option[String] = None)(): Set[CtValidation] = {

    def getIllegalCharacters(x: String): String = {
      val p = Pattern.compile(ValidCoHoCharacters)
      val m = p.matcher(x)
      val allMatchedCharsPluses = m.replaceAll("+")
      (allMatchedCharsPluses.toSet filterNot (_ == '+')).mkString("  ")
    }

    val errorCode = errorCodeBoxId.getOrElse(boxId)
    val illegalChars = getIllegalCharacters(value)
    passIf (illegalChars.isEmpty) {
      Set(CtValidation(Some(boxId), s"error.$errorCode.regexFailure", Some(Seq(illegalChars))))
    }
  }

  protected def validateOptionalStringByLengthMin(boxId: String, box: OptionalStringIdBox, min: Int)(): Set[CtValidation] = {
    box.value match {
      case Some(x) => validateNotEmptyStringByLengthMin(boxId, x, min)
      case _ => validationSuccess
    }
  }

  protected def validateOptionalStringByLengthMax(boxId: String, box: OptionalStringIdBox, max: Int)(): Set[CtValidation] = {
    box.value match {
      case Some(x) => validateNotEmptyStringByLength(boxId, x, 1, max)
      case _ => validationSuccess
    }
  }
  protected def validateOptionalStringByLength(boxId: String, box: OptionalStringIdBox, min: Int, max: Int)(): Set[CtValidation] = {
    box.value match {
      case Some(x) => validateNotEmptyStringByLength(boxId, x, min, max)
      case _ => validationSuccess
    }
  }

  protected def validateStringByLength(boxId: String, box: StringIdBox, min:Int, max:Int)(): Set[CtValidation] = {
     validateNotEmptyStringByLength(boxId, box.value, min, max)
  }

  def validateNotEmptyStringByLengthMin(boxId: String, value: String, min: Int)(): Set[CtValidation] = {
    failIf (value.nonEmpty && value.size < min ) {
      Set(CtValidation(Some(boxId), s"error.$boxId.text.sizeRange.min", Some(Seq(min.toString))))
    }
  }

  def validateNotEmptyStringByLength(boxId: String, value: String, min: Int, max: Int)(): Set[CtValidation] = {
    failIf (value.nonEmpty && value.size < min || value.size > max) {
      Set(CtValidation(Some(boxId), s"error.$boxId.text.sizeRange", Some(Seq(min.toString, max.toString))))
    }
  }

  def validateStringByLength(boxId: String, value: String, errorCodeId: String, min: Int, max: Int)(): Set[CtValidation] = {
    failIf (value.size < min || value.size > max) {
      Set(CtValidation(Some(boxId), s"error.$errorCodeId.text.sizeRange", Some(Seq(min.toString, max.toString))))
    }
  }


  /*
  This was labelled as @deprecated("", "29-09-2016 or earlier").
  This was used for a filing period before the date provided.
 */
  def validateStringMaxLength(boxId: String, value: String, max: Int)(): Set[CtValidation] = {
    failIf (value.size > max) {
      Set(CtValidation(Some(boxId), s"error.$boxId.max.length", Some(Seq(commaForThousands(max)))))
    }
  }

  def validatePostcode(boxId: String, box: OptionalStringIdBox)(): Set[CtValidation] = {
    validatePostcodeLength(boxId, box) ++ validatePostcodeRegex(boxId, box)
  }

  private def validatePostcodeLength(boxId: String, box: OptionalStringIdBox): Set[CtValidation] = {
    box.value match {
      case Some(x) if x.nonEmpty && x.size < 6 || x.size > 8 => Set(CtValidation(Some(boxId), s"error.$boxId.invalidPostcode"))
      case _ => validationSuccess
    }
  }

  private def validatePostcodeRegex(boxId: String, box: OptionalStringIdBox): Set[CtValidation] = {
    validateOptionalStringByRegex(boxId, box, postCodeRegex) match {
      case x if x.isEmpty => validationSuccess
      case _ => Set(CtValidation(Some(boxId), s"error.$boxId.invalidPostcode"))
    }
  }

  protected def atLeastOneBoxHasValue(boxId: String, boxes: OptionalCtValue[_]*)(): Set[CtValidation] = {
    if (noValue(boxes)) {
      Set(CtValidation(boxId = None, s"error.$boxId.one.box.required"))
    } else {
      Set.empty
    }
  }

  private def noValue(values: Seq[OptionalCtValue[_]]): Boolean = {
    values.forall(_.noValue)
  }

}

object ValidatableBox {

  val ValidNonForeignLessRestrictiveCharacters = "[A-Za-z0-9 ,\\.\\(\\)/&'\\-\"!%\\*_\\+:@<>\\?=;]*"
  val ValidNonForeignMoreRestrictiveCharacters = "[A-Za-z0-9 ,\\.\\(\\)/&'\\-\"]*"
  val ValidCoHoCharacters = "[a-zA-Z0-9‘’’&@£$€¥\\\\,.;:\\s!?/“”\"*=#%+<>»«_'(){}\\[\\]\\r-]*" // Based on the comment from CATO-4027
  val SortCodeValidChars = """^[0-9]{6}$"""
  val AccountNumberValidChars = """^[0-9]{8}$"""
  val StandardCohoTextFieldLimit = 20000
  val StandardCohoNameFieldLimit = 40
  val ValidCoHoNamesCharacters = "[A-Za-z\\-'\\. \\,]*" // Based on the comment from CATO-3881

  def commaForThousands(i: Int) = f"$i%,d"

  type OptionalBooleanIdBox = OptionalCtValue[Boolean] with CtBoxIdentifier
  type OptionalIntIdBox = OptionalCtValue[Int] with CtBoxIdentifier
  type OptionalStringIdBox = OptionalCtValue[String] with CtBoxIdentifier
  type StringIdBox = CtValue[String] with CtBoxIdentifier
  type OptionalDateIdBox = OptionalCtValue[LocalDate] with CtBoxIdentifier
  type OptionalBigDecimalIdBox = OptionalCtValue[BigDecimal] with CtBoxIdentifier

}
