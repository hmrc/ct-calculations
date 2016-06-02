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

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CPQ19(value: Option[Boolean]) extends CtBoxIdentifier(name = "Do you wish to claim your trading losses for this period against your profits for this period?")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[ComputationsBoxRetriever] {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._
    (retrieveCATO01().value, retrieveCP118().value, value) match {
      case (profit, loss, None) if profit > 0 && loss > 0 => Set(CtValidation(Some("CPQ19"), "error.CPQ19.required"))
      case (profit, loss, Some(_)) if profit > 0 && loss == 0 => Set(CtValidation(Some("CPQ19"), "error.CPQ19.cannot.exist.cp118"))
      case (profit, loss, Some(_)) if profit == 0 && loss > 0 => Set(CtValidation(Some("CPQ19"), "error.CPQ19.cannot.exist.cato01"))
      case (profit, loss, Some(_)) if profit == 0 && loss == 0 => Set(CtValidation(Some("CPQ19"), "error.CPQ19.cannot.exist"))
      case _ => Set.empty
    }
  }
}
