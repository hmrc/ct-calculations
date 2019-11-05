/*
 * Copyright 2019 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs105.validation

import uk.gov.hmrc.ct.accounts.frs105.boxes.{AC7999, AC7999a}
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtValidation, ValidatableBox}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

trait OffBalanceSheetArrangementsValidator {

  self: ValidatableBox[Frs105AccountsBoxRetriever with ComputationsBoxRetriever] with CtBoxIdentifier =>

  def validateAgainstAC7999a(boxRetriever: Frs105AccountsBoxRetriever, boxId: String, value: Option[String]): Set[CtValidation] = {
    (boxRetriever.ac7999a(), value) match {
      case (AC7999a(Some(true)), None) => validateStringAsMandatory(boxId, AC7999(value))
      case (AC7999a(Some(true)), Some("")) => validateStringAsMandatory(boxId, AC7999(value))
      case (_, _) => Set()
    }
  }
}
