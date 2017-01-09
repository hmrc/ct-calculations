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

package uk.gov.hmrc.ct.computations.Validators

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, CtValidation, ValidatableBox}
import uk.gov.hmrc.ct.computations.CPQ7
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

trait AllowancesQuestionsValidation {

  self: CtOptionalBoolean with ValidatableBox[ComputationsBoxRetriever] with CtBoxIdentifier =>

  def validateAgainstCPQ7(boxRetriever: ComputationsBoxRetriever, boxId: String, value: Option[Boolean]): Set[CtValidation] = {
    (boxRetriever.cpQ7(), value) match {
      case (CPQ7(Some(true)), None) => validateBooleanAsMandatory(boxId, this)
      case (CPQ7(Some(false)), Some(true)) => Set(CtValidation(Some(boxId), s"error.$boxId.notClaiming.required"))
      case _ => Set.empty
    }
  }
}
