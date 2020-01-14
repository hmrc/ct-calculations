/*
 * Copyright 2020 HM Revenue & Customs
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

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

trait DonationsValidation extends CtTypeConverters {

  self: ValidatableBox[ComputationsBoxRetriever] =>

  def validateLessThanTotalDonationsInPAndL(retriever: ComputationsBoxRetriever): Set[CtValidation] = {
    failIf(sumOfDonations(retriever) > retriever.cp29().value.getOrElse(0)) {
      Set(CtValidation(None, "error.sum.of.donations.exceeds.total"))
    }
  }

  def validateLessThanNetProfit(retriever: ComputationsBoxRetriever) : Set[CtValidation] = {
    failIf(retriever.cp999 > retriever.cato13) {
      Set(CtValidation(None, "error.qualifying.donations.exceeds.net.profit"))
    }
  }

  private def sumOfDonations(retriever: ComputationsBoxRetriever): Int = {
    retriever.cp303 + retriever.cp3030 + retriever.cp999
  }
}
