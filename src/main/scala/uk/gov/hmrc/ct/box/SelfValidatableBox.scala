/*
 * Copyright 2017 HM Revenue & Customs
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


  implicit def box2StringIdBox(box: CtValue[_] with CtBoxIdentifier): StringIdBox = box.asInstanceOf[StringIdBox]
  implicit def box2OptionalIntIdBox(box: CtValue[_] with CtBoxIdentifier): OptionalIntIdBox = box.asInstanceOf[OptionalIntIdBox]
  implicit def box2OptionalBooleanIdBox(box: CtValue[_] with CtBoxIdentifier): OptionalBooleanIdBox = box.asInstanceOf[OptionalBooleanIdBox]
  implicit def box2OptionalStringIdBox(box: CtValue[_] with CtBoxIdentifier): OptionalStringIdBox = box.asInstanceOf[OptionalStringIdBox]
  implicit def box2OptionalDateIdBox(box: CtValue[_] with CtBoxIdentifier): OptionalDateIdBox = box.asInstanceOf[OptionalDateIdBox]
  implicit def box2OptionalBigDecimalIdBox(box: CtValue[_] with CtBoxIdentifier): OptionalBigDecimalIdBox = box.asInstanceOf[OptionalBigDecimalIdBox]

  protected def validateBooleanAsMandatory()(): Set[CtValidation] = {
    super.validateBooleanAsMandatory(box.id, box)
  }

  protected def validateBooleanAsTrue()(): Set[CtValidation] = {
    super.validateBooleanAsTrue(box.id, box)
  }

  protected def validateIntegerAsMandatory()(): Set[CtValidation] = {
    super.validateIntegerAsMandatory(box.id, box)
  }

  protected def validateStringAsMandatory()(): Set[CtValidation] = {
    super.validateStringAsMandatory(box.id, box)
  }

  protected def validateAsMandatory()(): Set[CtValidation] = {
    validateAsMandatory(box)
  }

  protected def validateStringAsMandatoryIfPAYEEQ1False(boxRetriever: RepaymentsBoxRetriever)(): Set[CtValidation] = {
    super.validateStringAsMandatoryIfPAYEEQ1False(boxRetriever, box.id, box)
  }

  protected def validateAllFilledOrEmptyStrings(allBoxes: Set[CtValue[String]])(): Set[CtValidation] = {
    super.validateAllFilledOrEmptyStrings(box.id, allBoxes)
  }

  protected def validateAllFilledOrEmptyStringsForBankDetails(boxRetriever: RepaymentsBoxRetriever)(): Set[CtValidation] = {
    super.validateAllFilledOrEmptyStringsForBankDetails(boxRetriever, box.id)
  }

  protected def validateStringAsBlank()(): Set[CtValidation] = {
    super.validateStringAsBlank(box.id, box)
  }

  protected def validateDateAsMandatory()()(implicit ev: <:<[B, Option[LocalDate]]): Set[CtValidation] = {
    super.validateDateAsMandatory(box.id, box.value, box.id): Set[CtValidation]
  }

  protected def validateDateAsMandatory(date: Option[LocalDate], messageId: String)(): Set[CtValidation] = {
    super.validateDateAsMandatory(box.id, date, messageId)
  }

  protected def validateDateAsBlank()(): Set[CtValidation] = {
    super.validateDateAsBlank(box.id, box)
  }

  protected def validateDateAsBefore(dateToCompare: LocalDate)(): Set[CtValidation] = {
    super.validateDateAsBefore(box.id, box, dateToCompare)
  }

  protected def validateDateAsAfter(dateToCompare: LocalDate)(): Set[CtValidation] = {
    super.validateDateAsAfter(box.id, box, dateToCompare)
  }

  protected def validateDateAsBetweenInclusive(minDate: LocalDate, maxDate: LocalDate)()(implicit ev: <:<[B, Option[LocalDate]]): Set[CtValidation] = {
    validateDateAsBetweenInclusive(box.value, minDate, maxDate, box.id)
  }

  protected def validateDateAsBetweenInclusive(date: Option[LocalDate], minDate: LocalDate, maxDate: LocalDate, messageId: String)(): Set[CtValidation] = {
    super.validateDateAsBetweenInclusive(box.id, date, minDate, maxDate, messageId)
  }

  protected def validateIntegerAsBlank()(): Set[CtValidation] = {
    super.validateIntegerAsBlank(box.id, box)
  }

  protected def validateIntegerRange(min: Int = 0, max: Int)(): Set[CtValidation] = {
    super.validateIntegerRange(box.id, box, min, max)
  }

  protected def validateZeroOrPositiveBigDecimal()(): Set[CtValidation] = {
    super.validateZeroOrPositiveBigDecimal(box)
  }

  protected def validateZeroOrPositiveInteger()(): Set[CtValidation] = {
    super.validateZeroOrPositiveInteger(box)
  }

  protected def validatePositiveBigDecimal()(): Set[CtValidation] = {
    super.validatePositiveBigDecimal(box)
  }

  protected def validatePositiveInteger()(): Set[CtValidation] = {
    super.validatePositiveInteger(box)
  }

  protected def validateOptionalIntegerAsEqualTo(equalToBox: CtBoxIdentifier with CtOptionalInteger): Set[CtValidation] = {
    super.validateOptionalIntegerAsEqualTo(box.asInstanceOf[CtBoxIdentifier with CtOptionalInteger], equalToBox)
  }

  protected def validateOptionalStringByRegex(regex: String)(): Set[CtValidation] = {
    super.validateOptionalStringByRegex(box.id, box, regex)
  }

  protected def validateRawStringByRegex(value: String, errorCodeBoxId: String, regex: String)(): Set[CtValidation] = {
    super.validateRawStringByRegex(box.id, value, errorCodeBoxId, regex)
  }

  protected def validateStringByRegex(regex: String)(): Set[CtValidation] = {
    super.validateStringByRegex(box.id, box, regex)
  }

  protected def validateCoHoStringReturnIllegalChars()(): Set[CtValidation] = {
    super.validateCoHoStringReturnIllegalChars(box.id, box)
  }

  protected def validateCohoNameField()(): Set[CtValidation] = {
    super.validateCohoNameField(box.id, box)
  }

  protected def validateCohoOptionalNameField()(): Set[CtValidation] = {
    super.validateCohoOptionalNameField(box.id, box)
  }

  protected def validateCoHoStringReturnIllegalChars(value: String, errorCodeBoxId: Option[String])(): Set[CtValidation] = {
    super.validateCoHoStringReturnIllegalChars(box.id, value, errorCodeBoxId)
  }

  protected def validateOptionalStringByLength(min: Int, max: Int)(): Set[CtValidation] = {
    super.validateOptionalStringByLength(box.id, box, min, max)
  }

  protected def validateStringByLength(min:Int, max:Int)(): Set[CtValidation] = {
    super.validateStringByLength(box.id, box, min, max)
  }

  def validateOptionalIntegerLessOrEqualBox(other: CtBoxIdentifier with CtOptionalInteger)()(implicit ev: <:<[B, Option[Int]]): Set[CtValidation] = {
    failIf (box.value.nonEmpty && other.value.nonEmpty && box.value.get > other.value.get) {
      Set(CtValidation(Some(box.id), s"error.${box.id}.mustBeLessOrEqual.${other.id}"))
    }
  }

  def validateNotEmptyStringByLength(value: String, min: Int, max: Int)(): Set[CtValidation] = {
    super.validateNotEmptyStringByLength(box.id, value, min, max)
  }

  def validateStringByLength(value: String, errorCodeId: String, min: Int, max: Int)(): Set[CtValidation] = {
    super.validateStringByLength(box.id, value, errorCodeId, min, max)
  }

  @Deprecated
  def validateStringMaxLength(value: String, max: Int)(): Set[CtValidation] = {
    super.validateStringMaxLength(box.id, value, max)
  }

  def validatePostcode(boxId: String)()(implicit ev: <:<[B, Option[String]]): Set[CtValidation] = {
    super.validatePostcode(box.id, box)
  }

}
