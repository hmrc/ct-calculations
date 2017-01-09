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

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600e.v2.calculations.LoansAndDebtorsCalculator
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

case class E24e(value: Option[Int]) extends CtBoxIdentifier("Loans and non-trade debtors (Held at the end of the period)") with CtOptionalInteger

object E24e extends Calculated[E24e, CT600EBoxRetriever] with LoansAndDebtorsCalculator {
  override def calculate(boxRetriever: CT600EBoxRetriever): E24e =
    calculateFieldValue(
      boxRetriever.e24eA(),
      boxRetriever.e24eB()
    )
}
