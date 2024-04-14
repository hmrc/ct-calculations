/*
 * Copyright 2024 HM Revenue & Customs
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

case class CPQ17(value: Option[Boolean])
  extends CtBoxIdentifier(name = "Trading losses not used from previous accounting periods?")
    with CtOptionalBoolean
    with Input
    with ValidatableBox[ComputationsBoxRetriever]
    with TradingLossesValidation {
  import losses._

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    val lossesReformApplies = lossReform2017Applies(boxRetriever.cp2())

    collectErrors(
      requiredErrorIf({ value.isEmpty && (hasTradingProfit(boxRetriever) ||
        (lossesReformApplies && hasNonTradingProfit(boxRetriever))
        )}),
      cannotExistErrorIf(value.nonEmpty && (noTradingProfit(boxRetriever) &&
        !(lossesReformApplies && !noNonTradingProfit(boxRetriever))
        ))
    )
  }
}
