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

  def validate(boxRetriever: T): Set[CtValidation]

  protected def validateBooleanAsMandatory()()(implicit ev: <:<[B, Option[Boolean]]): Set[CtValidation] = {
    super.validateBooleanAsMandatory(box.id, box.asInstanceOf[OptionalBooleanIdBox])
  }

  protected def validateBooleanAsTrue()()(implicit ev: <:<[B, Option[Boolean]]): Set[CtValidation] = {
    super.validateBooleanAsTrue(box.id, box.asInstanceOf[OptionalBooleanIdBox])
  }

  protected def validateIntegerAsMandatory()()(implicit ev: <:<[B, Option[Int]]): Set[CtValidation] = {
    super.validateIntegerAsMandatory(box.id, box.asInstanceOf[OptionalIntIdBox])
  }

  protected def validateStringAsMandatory()()(implicit ev: <:<[B, Option[String]]): Set[CtValidation] = {
    super.validateStringAsMandatory(box.id, box.asInstanceOf[OptionalStringIdBox])
  }

  protected def validateAsMandatory()(): Set[CtValidation] = {
    validateAsMandatory(box)
  }

  protected def validateStringAsMandatoryIfPAYEEQ1False(boxRetriever: RepaymentsBoxRetriever)()(implicit ev: <:<[B, Option[String]]): Set[CtValidation] = {
    super.validateStringAsMandatoryIfPAYEEQ1False(boxRetriever, box.id, box.asInstanceOf[OptionalStringIdBox])
  }

  protected def validateAllFilledOrEmptyStrings(allBoxes: Set[CtValue[String]])(): Set[CtValidation] = {
    super.validateAllFilledOrEmptyStrings(box.id, allBoxes)
  }

  protected def validateAllFilledOrEmptyStringsForBankDetails(boxRetriever: RepaymentsBoxRetriever)(): Set[CtValidation] = {
    super.validateAllFilledOrEmptyStringsForBankDetails(boxRetriever, box.id)
  }

  protected def validateStringAsBlank()()(implicit ev: <:<[B, Option[String]]): Set[CtValidation] = {
    super.validateStringAsBlank(box.id, box.asInstanceOf[OptionalStringIdBox])
  }

  protected def validateDateAsMandatory()()(implicit ev: <:<[B, Option[LocalDate]]): Set[CtValidation] = {
    super.validateDateAsMandatory(box.id, box.value.asInstanceOf[Option[LocalDate]], box.id): Set[CtValidation]
  }

  protected def validateDateAsMandatory(date: Option[LocalDate], messageId: String)(): Set[CtValidation] = {
    super.validateDateAsMandatory(box.id, date, messageId)
  }

  protected def validateDateAsBlank()()(implicit ev: <:<[B, Option[LocalDate]]): Set[CtValidation] = {
    super.validateDateAsBlank(box.id, box.asInstanceOf[OptionalDateIdBox])
  }

  protected def validateDateAsBefore(dateToCompare: LocalDate)()(implicit ev: <:<[B, Option[LocalDate]]): Set[CtValidation] = {
    super.validateDateAsBefore(box.id, box.asInstanceOf[OptionalDateIdBox], dateToCompare)
  }

  protected def validateDateAsAfter(dateToCompare: LocalDate)()(implicit ev: <:<[B, Option[LocalDate]]): Set[CtValidation] = {
    super.validateDateAsAfter(box.id, box.asInstanceOf[OptionalDateIdBox], dateToCompare)
  }

  protected def validateDateAsBetweenInclusive(minDate: LocalDate, maxDate: LocalDate)()(implicit ev: <:<[B, Option[LocalDate]]): Set[CtValidation] = {
    validateDateAsBetweenInclusive(box.value.asInstanceOf[Option[LocalDate]], minDate, maxDate, box.id)
  }

  protected def validateDateAsBetweenInclusive(date: Option[LocalDate], minDate: LocalDate, maxDate: LocalDate, messageId: String)(): Set[CtValidation] = {
    super.validateDateAsBetweenInclusive(box.id, date, minDate, maxDate, messageId)
  }

  protected def validateIntegerAsBlank()()(implicit ev: <:<[B, Option[Int]]): Set[CtValidation] = {
    super.validateIntegerAsBlank(box.id, box.asInstanceOf[OptionalIntIdBox])
  }

  protected def validateIntegerRange(min: Int = 0, max: Int)()(implicit ev: <:<[B, Option[Int]]): Set[CtValidation] = {
    super.validateIntegerRange(box.id, box.asInstanceOf[OptionalIntIdBox], min, max)
  }

  protected def validateZeroOrPositiveBigDecimal()()(implicit ev: <:<[B, Option[BigDecimal]]): Set[CtValidation] = {
    super.validateZeroOrPositiveBigDecimal(box.asInstanceOf[OptionalBigDecimalIdBox])
  }

  protected def validateZeroOrPositiveInteger()()(implicit ev: <:<[B, Option[Int]]): Set[CtValidation] = {
    super.validateZeroOrPositiveInteger(box.asInstanceOf[OptionalIntIdBox])
  }

  protected def validatePositiveBigDecimal()()(implicit ev: <:<[B, Option[BigDecimal]]): Set[CtValidation] = {
    super.validatePositiveBigDecimal(box.asInstanceOf[OptionalBigDecimalIdBox])
  }

  protected def validatePositiveInteger()()(implicit ev: <:<[B, Option[Int]]): Set[CtValidation] = {
    super.validatePositiveInteger(box.asInstanceOf[OptionalIntIdBox])
  }

  protected def validateOptionalIntegerAsEqualTo(equalToBox: CtBoxIdentifier with CtOptionalInteger)(implicit ev: <:<[B, CtBoxIdentifier with CtOptionalInteger]): Set[CtValidation] = {
    super.validateOptionalIntegerAsEqualTo(box.asInstanceOf[CtBoxIdentifier with CtOptionalInteger], equalToBox)
  }

  protected def validateOptionalStringByRegex(regex: String)()(implicit ev: <:<[B, Option[String]]): Set[CtValidation] = {
    super.validateOptionalStringByRegex(box.id, box.asInstanceOf[OptionalStringIdBox], regex)
  }

  protected def validateRawStringByRegex(value: String, errorCodeBoxId: String, regex: String)(): Set[CtValidation] = {
    super.validateRawStringByRegex(box.id, value, errorCodeBoxId, regex)
  }

  protected def validateStringByRegex(regex: String)()(implicit ev: <:<[B, String]): Set[CtValidation] = {
    super.validateStringByRegex(box.id, box.asInstanceOf[StringIdBox], regex)
  }

  protected def validateCoHoOptionalString()()(implicit ev: <:<[B, Option[String]]): Set[CtValidation] = {
    super.validateCoHoOptionalString(box.id, box.asInstanceOf[OptionalStringIdBox])
  }

  protected def validateCohoNameField()()(implicit ev: <:<[B, String]): Set[CtValidation] = {
    super.validateCohoNameField(box.id, box.asInstanceOf[StringIdBox])
  }

  protected def validateCohoOptionalNameField()()(implicit ev: <:<[B, Option[String]]): Set[CtValidation] = {
    super.validateCohoOptionalNameField(box.id, box.asInstanceOf[OptionalStringIdBox])
  }

  protected def validateCoHoString(value: String, errorCodeBoxId: Option[String])(): Set[CtValidation] = {
    super.validateCoHoString(box.id, value, errorCodeBoxId)
  }

  protected def validateOptionalStringByLength(min: Int, max: Int)()(implicit ev: <:<[B, Option[String]]): Set[CtValidation] = {
    super.validateOptionalStringByLength(box.id, box.asInstanceOf[OptionalStringIdBox], min, max)
  }

  protected def validateStringByLength(min:Int, max:Int)()(implicit ev: <:<[B, String]): Set[CtValidation] = {
    super.validateStringByLength(box.id, box.asInstanceOf[StringIdBox], min, max)
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

  def validatePostcode(boxId: String)()(implicit ev: <:<[B, Option[String]]): Set[CtValidation] = {
    super.validatePostcode(box.id, box.asInstanceOf[OptionalStringIdBox])
  }

}

