/*
 * Copyright 2015 HM Revenue & Customs
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

import uk.gov.hmrc.ct.box.retriever.BoxRetriever

trait ValidatableBox[T <: BoxRetriever] {

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
      case _ => Set()
    }
  }

//  protected def validateStringAsDependentMandatory(boxId: String, box: CtOptionalString, dependentBox:CtOptionalBoolean): Set[CtValidation] = {
//    (dependentBox.value, box.value) match {
////      case None => Set(CtValidation(Some(boxId), s"error.$boxId.required"))
////      case _ => Set()
//      case (Some(true))
//      case  (None, None) => Set()
//    }
//  }

  protected def validateStringAsBlank(boxId: String, box: CtOptionalString): Set[CtValidation] = {
    box.value match {
      case None => Set()
      case Some(x) if x.isEmpty => Set()
      case _ => Set(CtValidation(Some(boxId), s"error.$boxId.nonBlankValue"))
    }
  }

  protected def validateDateAsMandatory(boxId: String, box: CtOptionalDate): Set[CtValidation] = {
    box.value match {
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

  protected def validateIntegerRange(boxId: String, box: CtOptionalInteger, min: Int = 0, max: Int): Set[CtValidation] = {
    box.value match {
      case Some(x) => {
        if (min <= x && x <= max) Set()
        else Set(CtValidation(Some(boxId), s"error.$boxId.outOfRange"))
      }
      case _ => Set()
    }
  }

  protected def validateStringByRegex(boxId: String, box: CtOptionalString, regex: String): Set[CtValidation] = {
    box.value match {
      case Some(x) if x.nonEmpty => {
        if (x.matches(regex)) Set()
        else Set(CtValidation(Some(boxId), s"error.$boxId.regexFailure"))
      }
      case _ => Set()
    }
  }


}
