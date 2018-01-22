/*
 * Copyright 2018 HM Revenue & Customs
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

case class CP281c(value: Option[Int]) extends CtBoxIdentifier("NIR Losses brought forward from on or after 01/04/2017")
  with CtOptionalInteger
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with TradingLossesValidation {

  override def validate(retriever: ComputationsBoxRetriever): Set[CtValidation] = {
    import uk.gov.hmrc.ct.computations.losses._
    import uk.gov.hmrc.ct.computations.nir._

    collectErrors(
      requiredErrorIf(retriever.cpQ17().isTrue &&
                      lossReform2017Applies(retriever.cp2()) &&
                      mayHaveNirLosses(retriever) &&
                      !hasValue),
      cannotExistErrorIf(
        hasValue &&
        (retriever.cpQ17().isFalse ||
          !lossReform2017Applies(retriever.cp2()) ||
          !mayHaveNirLosses(retriever))
      ),
      validateIntegerRange("CP281c", this, 0, retriever.cp281b())
    )
  }
}
