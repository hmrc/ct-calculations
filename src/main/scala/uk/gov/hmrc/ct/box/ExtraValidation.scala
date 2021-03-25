/*
 * Copyright 2021 HM Revenue & Customs
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
import uk.gov.hmrc.ct.domain.ValidationConstants.toErrorArgsFormat
import uk.gov.hmrc.ct.utils.DateImplicits._

trait ExtraValidation extends Validators {

  private val validationSuccess: Set[CtValidation] = Set()
  protected val postCodeRegex = """(GIR 0AA)|((([A-Z][0-9][0-9]?)|(([A-Z][A-HJ-Y][0-9][0-9]?)|(([A-Z][0-9][A-Z])|([A-Z][A-HJ-Y][0-9]?[A-Z])))) [0-9][A-Z]{2})"""

  def validateAsMandatory[A](boxId: String, value: Option[A])(): Set[CtValidation] = {
    value match {
      case None => Set(CtValidation(Some(boxId), s"error.$boxId.required"))
      case Some(x: String) if x.isEmpty => Set(CtValidation(Some(boxId), s"error.$boxId.required"))
      case _ => validationSuccess
    }
  }

  def validateZeroOrPositiveInteger(boxId: String, value: Option[Int]): Set[CtValidation] = {
    value match {
      case Some(x) if x < 0 => Set(CtValidation(Some(boxId), s"error.$boxId.mustBeZeroOrPositive"))
      case _ => validationSuccess
    }
  }

  def validateDateIsInclusive(boxId: String, minDate: LocalDate, dateToCompare: Option[LocalDate], maxDate: LocalDate): Set[CtValidation] = {
    dateToCompare match {
      case None => validationSuccess
      case Some(dateToComp) if minDate <= dateToComp && dateToComp <= maxDate => validationSuccess
      case _ => Set(CtValidation(Some(boxId), s"error.$boxId.not.betweenInclusive", Some(Seq(toErrorArgsFormat(minDate), toErrorArgsFormat(maxDate)))))
    }
  }

  def validatePostcode(boxId: String, postcode: Option[String]) =
    if(postcode.isEmpty) {
      validationSuccess
    } else {
      validatePostcodeLength(boxId, postcode.get) ++ validatePostcodeRegex(boxId, postcode.get)
    }
    def validatePostcodeLength(boxId: String, postcode: String): Set[CtValidation] = {
      if (postcode.size < 6 || postcode.size > 8) Set(CtValidation(Some(boxId), s"error.$boxId.invalidPostcode"))
      else validationSuccess
    }

  def validatePostcodeRegex(boxId: String, postcode: String): Set[CtValidation] =
    validateStringByRegex(boxId, postcode, postCodeRegex)

 def validateStringByRegex(boxId: String, str: String, regex: String): Set[CtValidation] =
   passIf(str.matches(regex)) {
     Set(CtValidation(Some(boxId), s"error.$boxId.invalidPostcode"))
   }

  def validateOptionalStringByLength(value: Option[String], min: Int, max: Int, boxId: String, boxIdPrefix: Option[String])(): Set[CtValidation] = {
    value match {
      case Some(x) if(x.nonEmpty && x.size < min || x.size > max) => Set(CtValidation(Some(boxIdPrefix.getOrElse("")+boxId), s"error.$boxId.text.sizeRange", Some(Seq(min.toString, max.toString))))
      case _ => validationSuccess
    }
  }
}
