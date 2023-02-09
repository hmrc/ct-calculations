/*
 * Copyright 2023 HM Revenue & Customs
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
import uk.gov.hmrc.ct.computations.Validators.DonationsValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import CP3020.grassrootsStart

case class CPQ321(value: Option[Boolean]) extends CtBoxIdentifier(name = "Did your company make any donations to grassroots sports?")
  with CtOptionalBoolean with Input with ValidatableBox[ComputationsBoxRetriever] with DonationsValidation {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      failIf(!boxRetriever.cp2.value.isBefore(grassrootsStart) && noValue) {
        Set(CtValidation(Some("CPQ321"), s"error.CPQ321.required"))
      },
      validateLessThanTotalDonationsInPAndL(boxRetriever),
      validateLessThanNetProfit(boxRetriever),
      failIf(isTrue && !boxRetriever.cp3010.isPositive && !boxRetriever.cp3020.isPositive && !boxRetriever.cp3030.isPositive) {
        Set(CtValidation(None, "error.CPQ321.no.grassroots.donations"))
      }
    )
  }
}
