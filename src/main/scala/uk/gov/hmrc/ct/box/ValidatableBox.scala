/*
 * Copyright 2016 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ct.box

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.ct600.v3.retriever.RepaymentsBoxRetriever
import uk.gov.hmrc.ct.domain.ValidationConstants._

trait ValidatableBox[T <: BoxRetriever] {


  val validNonForeignLessRestrictiveCharacters = "[A-Za-z0-9 ,\\.\\(\\)/&'\\-\"!%\\*_\\+:@<>\\?=;]*"
  val validNonForeignMoreRestrictiveCharacters = "[A-Za-z0-9 ,\\.\\(\\)/&'\\-\"]*"
  val validCoHoCharacters = "[A-Za-z\\-'\\. \\,]*" // Based on the comment from CATO-3881
  val SortCodeValidChars = """^[0-9]{6}$"""
  val AccountNumberValidChars = """^[0-9]{8}$"""

  // Taken from PostCodeType on http://www.hmrc.gov.uk/schemas/core-v2-0.xsd
  private val postCodeRegex = """(GIR 0AA)|((([A-Z][0-9][0-9]?)|(([A-Z][A-HJ-Y][0-9][0-9]?)|(([A-Z][0-9][A-Z])|([A-Z][A-HJ-Y][0-9]?[A-Z])))) [0-9][A-Z]{2})"""


  def validate(boxRetriever: T): Set[CtValidation]

  protected def validateBooleanAsMandatory(boxId: String, box: CtOptionalBoolean): Set[CtValidation] = {
    box.value match {
      case None => Set(CtValidation(Some(boxId), s"error.$boxId.required"))
      case _ => Set()
    }
  }

  protected def validateIntegerAsMandatory(boxId: String, box: CtOptionalInteger): Set[CtValidation] = {
    box.value match {
      case None => Set(CtValidation(Some(boxId), s"error.$boxId.required"))
      case _ => Set()
    }
  }

  protected def validateStringAsMandatory(boxId: String, box: CtOptionalString): Set[CtValidation] = {
    box.value match {
      case None => Set(CtValidation(Some(boxId), s"error.$boxId.required"))
      case Some(x) if x.isEmpty => Set(CtValidation(Some(boxId), s"error.$boxId.required"))
      case _ => Set()
    }
  }

  protected def validateAsMandatory[U](box: CtValue[U] with CtBoxIdentifier): Set[CtValidation] = {
    box.value match {
      case None => Set(CtValidation(Some(box.id), s"error.${box.id}.required"))
      case _ => Set()
    }
  }

  protected def validateStringAsMandatoryIfPAYEEQ1False(boxRetriever: RepaymentsBoxRetriever, boxId: String, box: CtOptionalString): Set[CtValidation] = {
    val payeeq1 = boxRetriever.retrievePAYEEQ1()
    if (!payeeq1.value.getOrElse(true)) {
      validateStringAsMandatory(boxId, box)
    } else Set()
  }

  protected def validateAllFilledOrEmptyStrings(boxId: String, allBoxes: Set[CtString]): Set[CtValidation] = {
    val allEmpty = allBoxes.count(_.value.isEmpty) == allBoxes.size
    val allNonEmpty = allBoxes.count(_.value.nonEmpty) == allBoxes.size

    passIf(allEmpty || allNonEmpty) {
      Set(CtValidation(Some(boxId), s"error.$boxId.allornone"))
    }
  }

  protected def validateAllFilledOrEmptyStringsForBankDetails(boxRetriever: RepaymentsBoxRetriever, boxId: String): Set[CtValidation] = {
    val bankDetailsBoxGroup:Set[CtString] = Set(
      boxRetriever.retrieveB920(),
      boxRetriever.retrieveB925(),
      boxRetriever.retrieveB930(),
      boxRetriever.retrieveB935()
    )
    validateAllFilledOrEmptyStrings(boxId, bankDetailsBoxGroup)
  }

  protected def validateStringAsBlank(boxId: String, box: CtOptionalString): Set[CtValidation] = {
    box.value match {
      case None => Set()
      case Some(x) if x.isEmpty => Set()
      case _ => Set(CtValidation(Some(boxId), s"error.$boxId.nonBlankValue"))
    }
  }

  protected def validateDateAsMandatory(boxId: String, box: CtOptionalDate): Set[CtValidation] = {
    validateDateAsMandatory(boxId, box.value): Set[CtValidation]
  }

  protected def validateDateAsMandatory(boxId: String, date: Option[LocalDate]): Set[CtValidation] = {
    date match {
      case None => Set(CtValidation(Some(boxId), s"error.$boxId.required"))
      case _ => Set()
    }
  }

  protected def validateDateAsBlank(boxId: String, box: CtOptionalDate): Set[CtValidation] = {
    box.value match {
      case None => Set()
      case _ => Set(CtValidation(Some(boxId), s"error.$boxId.nonBlankValue"))
    }
  }

  protected def validateDateAsBefore(boxId: String, box: CtOptionalDate, dateToCompare: LocalDate): Set[CtValidation] = {
    box.value match {
      case None => Set()
      case Some(date) if date.isBefore(dateToCompare) => Set()
      case _ => Set(CtValidation(Some(boxId), s"error.$boxId.not.before"))
    }
  }

  protected def validateDateAsAfter(boxId: String, box: CtOptionalDate, dateToCompare: LocalDate): Set[CtValidation] = {
    box.value match {
      case None => Set()
      case Some(date) if date.isAfter(dateToCompare) => Set()
      case _ => Set(CtValidation(Some(boxId), s"error.$boxId.not.after"))
    }
  }

  protected def validateDateAsBetweenInclusive(boxId: String, box: CtOptionalDate, minDate: LocalDate, maxDate: LocalDate): Set[CtValidation] = {
    validateDateAsBetweenInclusive(boxId, box.value, minDate, maxDate)
  }

  protected def validateDateAsBetweenInclusive(boxId: String, date: Option[LocalDate], minDate: LocalDate, maxDate: LocalDate): Set[CtValidation] = {
    date match {
      case None => Set()
      case Some(date) if date.isBefore(minDate.toDateTimeAtStartOfDay.toLocalDate) || date.isAfter(maxDate.plusDays(1).toDateTimeAtStartOfDay.minusSeconds(1).toLocalDate) =>
        Set(CtValidation(Some(boxId), s"error.$boxId.not.betweenInclusive", Some(Seq(toErrorArgsFormat(minDate), toErrorArgsFormat(maxDate)))))
      case _ => Set()
    }
  }

  protected def validateIntegerAsBlank(boxId: String, box: CtOptionalInteger): Set[CtValidation] = {
    box.value match {
      case None => Set()
      case _ => Set(CtValidation(Some(boxId), s"error.$boxId.nonBlankValue"))
    }
  }

  protected def validateIntegerRange(boxId: String, box: CtOptionalInteger, min: Int = 0, max: Int): Set[CtValidation] = {
    box.value match {
      case Some(x) => {
        passIf (min <= x && x <= max) {
           Set(CtValidation(Some(boxId), s"error.$boxId.outOfRange", Some(Seq(min.toString,max.toString))))
        }
      }
      case _ => Set()
    }
  }

  protected def validateZeroOrPositiveBigDecimal(box: CtOptionalBigDecimal with CtBoxIdentifier): Set[CtValidation] = {
    box.value match {
      case Some(x) if x < BigDecimal(0) => Set(CtValidation(Some(box.id), s"error.${box.id}.mustBeZeroOrPositive"))
      case _ => Set()
    }
  }

  protected def validateZeroOrPositiveInteger(box: CtOptionalInteger with CtBoxIdentifier): Set[CtValidation] = {
    box.value match {
      case Some(x) if x < 0 => Set(CtValidation(Some(box.id), s"error.${box.id}.mustBeZeroOrPositive"))
      case _ => Set()
    }
  }

  protected def validatePositiveBigDecimal(box: CtOptionalBigDecimal with CtBoxIdentifier): Set[CtValidation] = {
    box.value match {
      case Some(x) if x <= 0 => Set(CtValidation(Some(box.id), s"error.${box.id}.mustBePositive"))
      case _ => Set()
    }
  }

  protected def validatePositiveInteger(box: CtOptionalInteger with CtBoxIdentifier): Set[CtValidation] = {
    box.value match {
      case Some(x) if x <= 0 => Set(CtValidation(Some(box.id), s"error.${box.id}.mustBePositive"))
      case _ => Set()
    }
  }

  protected def validateOptionalStringByRegex(boxId: String, box: CtOptionalString, regex: String): Set[CtValidation] = {
    box.value match {
      case Some(x) if x.nonEmpty => {
        passIf (x.matches(regex)) {
          Set(CtValidation(Some(boxId), s"error.$boxId.regexFailure"))
        }
      }
      case _ => Set()
    }
  }

  protected def validateStringByRegex(boxId: String, box: CtString, regex: String): Set[CtValidation] = {
    passIf (box.value.isEmpty || box.value.matches(regex)) {
      Set(CtValidation(Some(boxId), s"error.$boxId.regexFailure"))
    }
  }

  protected def validateStringByRegex(boxId: String, value: String, errorCodeBoxId: String, regex: String): Set[CtValidation] = {
    passIf (value.matches(regex)) {
      Set(CtValidation(Some(boxId), s"error.$errorCodeBoxId.regexFailure"))
    }
  }

  protected def validateOptionalStringByLength(boxId: String, box: CtOptionalString, min: Int, max: Int): Set[CtValidation] = {
    box.value match {
      case Some(x) => validateNotEmptyStringByLength(boxId, x, min, max)
      case _ => Set()
    }
  }

  protected def validateStringByLength(boxId: String, box: CtString, min:Int, max:Int): Set[CtValidation] = {
     validateNotEmptyStringByLength(boxId, box.value, min, max)
  }

  def validateNotEmptyStringByLength(boxId: String, value: String, min: Int, max: Int): Set[CtValidation] = {
    failIf (value.nonEmpty && value.size < min || value.size > max) {
      Set(CtValidation(Some(boxId), s"error.$boxId.text.sizeRange", Some(Seq(min.toString, max.toString))))
    }
  }

  def validateStringByLength(boxId: String, value: String, errorCodeId: String, min: Int, max: Int): Set[CtValidation] = {
    failIf (value.size < min || value.size > max) {
      Set(CtValidation(Some(boxId), s"error.$errorCodeId.text.sizeRange", Some(Seq(min.toString, max.toString))))
    }
  }

  def validatePostcode(boxId: String, box: CtOptionalString): Set[CtValidation] = {
    validatePostcodeLength(boxId, box) ++ validatePostcodeRegex(boxId, box)
  }

  private def validatePostcodeLength(boxId: String, box: CtOptionalString): Set[CtValidation] = {
    box.value match {
      case Some(x) if x.nonEmpty && x.size < 6 || x.size > 8 => Set(CtValidation(Some(boxId), s"error.$boxId.invalidPostcode"))
      case _ => Set()
    }
  }

  private def validatePostcodeRegex(boxId: String, box: CtOptionalString): Set[CtValidation] = {
    validateOptionalStringByRegex(boxId, box, postCodeRegex) match {
      case x if x.isEmpty => Set()
      case _ => Set(CtValidation(Some(boxId), s"error.$boxId.invalidPostcode"))
    }
  }

  object failIf {
    def apply(condition: => Boolean)(validationErrors: => Set[CtValidation]): Set[CtValidation] = if(condition) validationErrors else Set()
  }

  object passIf {
    def apply(condition: => Boolean)(validationErrors: => Set[CtValidation]): Set[CtValidation] = if(condition) Set() else validationErrors
  }
}
