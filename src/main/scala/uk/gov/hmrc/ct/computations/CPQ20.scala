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

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.Validators.TradingLossesValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CPQ20(value: Option[Boolean]) extends CtBoxIdentifier(name = "Is the company claiming to carry losses of this period back to an earlier period")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with TradingLossesValidation {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._
    import CPQ20._

    (value, cpQ19().value, cp118().value, cato01().value) match {
      case (None, Some(true), cp118, cato01) if moreLossesThanNonTradeProfit(cp118, cato01) => requiredError
      case (None, None, cp118, cato01) if moreLossesThanNonTradeProfit(cp118, cato01) && cato01 == 0 => requiredError

      case (Some(_), None, cp118, cato01) if noLossesWithNonTradingProfit(cp118, cato01) => cannotExistError
      case (Some(true), Some(true), cp118, cato01) if allLossesOffsetByNonTradingProfit(cp118, cato01) => cannotExistError
      case (Some(_), Some(false), _, _) => cannotExistError
      case (Some(false), _, cp118, cato01) if allLossesOffsetByNonTradingProfit(cp118, cato01) => cannotExistError

      case _ => Set.empty
    }
  }
}

object CPQ20 {
  val cannotExistError = Set(CtValidation(Some("CPQ20"), "error.CPQ20.cannot.exist"))
  val requiredError = Set(CtValidation(Some("CPQ20"), "error.CPQ20.required"))
}
