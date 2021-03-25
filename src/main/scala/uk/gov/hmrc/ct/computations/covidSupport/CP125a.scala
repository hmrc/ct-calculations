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

package uk.gov.hmrc.ct.computations.covidSupport

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, CtValidation, Input, ValidatableBox, Validators}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP125a(value: Option[Int])extends CtBoxIdentifier(name = "Enter the amount of Eat Out to Help Out overpayments not repaid") with
  CtOptionalInteger with Input with ValidatableBox[ComputationsBoxRetriever] with Validators {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    val amountReceivedFromEotho = boxRetriever.cp121().orZero

    collectErrors(
      validateZeroOrPositiveInteger(this),
      exceedsMax(value, amountReceivedFromEotho)
    )
  }
}
