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
import uk.gov.hmrc.ct.utils.DateImplicits._
import ValidatableBox._


trait SelfValidatableBox[T <: BoxRetriever, B] extends Validators with ValidatableBox[T]{

  box: CtValue[B] with CtBoxIdentifier =>

  // Taken from PostCodeType on http://www.hmrc.gov.uk/schemas/core-v2-0.xsd
  private val postCodeRegex = """(GIR 0AA)|((([A-Z][0-9][0-9]?)|(([A-Z][A-HJ-Y][0-9][0-9]?)|(([A-Z][0-9][A-Z])|([A-Z][A-HJ-Y][0-9]?[A-Z])))) [0-9][A-Z]{2})"""

  def validate(boxRetriever: T): Set[CtValidation]

  protected def validateBooleanAsMandatory()(): Set[CtValidation] = {
    super.validateBooleanAsMandatory(box.id, box.asInstanceOf[CtValue[Option[Boolean]] with CtBoxIdentifier])
  }

  protected def validateIntegerAsMandatory()(): Set[CtValidation] = {
    super.validateIntegerAsMandatory(box.id, box.asInstanceOf[CtValue[Option[Int]] with CtBoxIdentifier])
  }

  protected def validateStringAsMandatory()()(implicit ev: <:<[B, String]): Set[CtValidation] = {
    super.validateStringAsMandatory(box.id, box.asInstanceOf[CtValue[Option[String]] with CtBoxIdentifier])
  }

  protected def validateAsMandatory()(): Set[CtValidation] = {
    validateAsMandatory(box)
  }

  protected def validateStringAsMandatoryIfPAYEEQ1False(boxRetriever: RepaymentsBoxRetriever)(): Set[CtValidation] = {
    super.validateStringAsMandatoryIfPAYEEQ1False(boxRetriever, box.id, box.asInstanceOf[CtValue[Option[String]] with CtBoxIdentifier])
  }

  protected def validateAllFilledOrEmptyStrings(allBoxes: Set[CtValue[String]])(): Set[CtValidation] = {
    super.validateAllFilledOrEmptyStrings(box.id, allBoxes)
  }

  protected def validateAllFilledOrEmptyStringsForBankDetails(boxRetriever: RepaymentsBoxRetriever)(): Set[CtValidation] = {
    super.validateAllFilledOrEmptyStringsForBankDetails(boxRetriever, box.id)
  }

  protected def validateStringAsBlank()(): Set[CtValidation] = {
    super.validateStringAsBlank(box.id, box.asInstanceOf[CtValue[Option[String]] with CtBoxIdentifier])
  }

  protected def validateDateAsMandatory()(): Set[CtValidation] = {
    super.validateDateAsMandatory(box.id, box.value.asInstanceOf[Option[LocalDate]], box.id): Set[CtValidation]
  }

  protected def validateDateAsMandatory(date: Option[LocalDate], messageId: String)(): Set[CtValidation] = {
    super.validateDateAsMandatory(box.id, date, messageId)
  }

  protected def validateDateAsBlank()(): Set[CtValidation] = {
    super.validateDateAsBlank(box.id, box.asInstanceOf[CtValue[Option[LocalDate]] with CtBoxIdentifier])
  }

  protected def validateDateAsBefore(dateToCompare: LocalDate)(): Set[CtValidation] = {
    super.validateDateAsBefore(box.id, box.asInstanceOf[CtValue[Option[LocalDate]] with CtBoxIdentifier], dateToCompare)
  }

  protected def validateDateAsAfter(dateToCompare: LocalDate)(): Set[CtValidation] = {
    super.validateDateAsAfter(box.id, box.asInstanceOf[CtValue[Option[LocalDate]] with CtBoxIdentifier], dateToCompare)
  }

  protected def validateDateAsBetweenInclusive(minDate: LocalDate, maxDate: LocalDate)(): Set[CtValidation] = {
    validateDateAsBetweenInclusive(box.value.asInstanceOf[Option[LocalDate]], minDate, maxDate, box.id)
  }

  protected def validateDateAsBetweenInclusive(date: Option[LocalDate], minDate: LocalDate, maxDate: LocalDate, messageId: String)(): Set[CtValidation] = {
    super.validateDateAsBetweenInclusive(box.id, date, minDate, maxDate, messageId)
  }

  protected def validateIntegerAsBlank()(): Set[CtValidation] = {
    super.validateIntegerAsBlank(box.id, box.asInstanceOf[CtValue[Option[Int]] with CtBoxIdentifier])
  }

  protected def validateIntegerRange(min: Int = 0, max: Int)(): Set[CtValidation] = {
    super.validateIntegerRange(box.id, box.asInstanceOf[CtValue[Option[Int]] with CtBoxIdentifier], min, max)
  }

  protected def validateZeroOrPositiveBigDecimal()(): Set[CtValidation] = {
    super.validateZeroOrPositiveBigDecimal(box.asInstanceOf[CtValue[Option[BigDecimal]] with CtBoxIdentifier])
  }

  protected def validateZeroOrPositiveInteger()(): Set[CtValidation] = {
    super.validateZeroOrPositiveInteger(box.asInstanceOf[CtValue[Option[Int]] with CtBoxIdentifier])
  }

  protected def validatePositiveBigDecimal()(): Set[CtValidation] = {
    super.validatePositiveBigDecimal(box.asInstanceOf[CtValue[Option[BigDecimal]] with CtBoxIdentifier])
  }

  protected def validatePositiveInteger()(): Set[CtValidation] = {
    super.validatePositiveInteger(box.asInstanceOf[CtValue[Option[Int]] with CtBoxIdentifier])
  }

  protected def validateOptionalIntegerAsEqualTo(equalToBox: CtBoxIdentifier with CtOptionalInteger): Set[CtValidation] = {
    super.validateOptionalIntegerAsEqualTo(box.asInstanceOf[CtBoxIdentifier with CtOptionalInteger], equalToBox)
  }

  protected def validateOptionalStringByRegex(regex: String)()(implicit ev: <:<[B, String]): Set[CtValidation] = {
    super.validateOptionalStringByRegex(box.id, box.asInstanceOf[CtValue[Option[String]] with CtBoxIdentifier], regex)
  }

  protected def validateCoHoOptionalTextField()(): Set[CtValidation] = {
    super.validateCoHoOptionalTextField(box.id, box.asInstanceOf[CtValue[Option[String]] with CtBoxIdentifier])
  }

  protected def validateStringByRegex(boxValue: CtString, regex: String)(): Set[CtValidation] = {
    super.validateStringByRegex(box.id, boxValue, regex)
  }

  protected def validateRawStringByRegex(value: String, errorCodeBoxId: String, regex: String)(): Set[CtValidation] = {
    super.validateRawStringByRegex(box.id, value, errorCodeBoxId, regex)
  }

  protected def validateOptionalStringByLength(min: Int, max: Int)()(implicit ev: <:<[B, String]): Set[CtValidation] = {
    super.validateOptionalStringByLength(box.id, box.asInstanceOf[CtValue[Option[String]] with CtBoxIdentifier], min, max)
  }

  protected def validateStringByLength(boxValue: CtString, min:Int, max:Int)(): Set[CtValidation] = {
    super.validateStringByLength(box.id, boxValue, min, max)
  }

  def validateNotEmptyStringByLength(value: String, min: Int, max: Int)(): Set[CtValidation] = {
    super.validateNotEmptyStringByLength(box.id, value, min, max)
  }

  def validateStringByLength(value: String, errorCodeId: String, min: Int, max: Int)(): Set[CtValidation] = {
    super.validateStringByLength(box.id, value, errorCodeId, min, max)
  }

  def validateStringMaxLength(value: String, max: Int)(): Set[CtValidation] = {
    super.validateStringMaxLength(box.id, value, max)
  }

  def validatePostcode(boxId: String)(): Set[CtValidation] = {
    super.validatePostcode(box.id, box.asInstanceOf[CtValue[Option[String]] with CtBoxIdentifier])
  }

}

