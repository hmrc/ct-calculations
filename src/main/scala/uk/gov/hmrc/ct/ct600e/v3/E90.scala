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

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600e.v3.calculations.IncomeCalculator
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

case class E90(value: Option[Int]) extends CtBoxIdentifier("Total of boxes E50 to E85") with CtOptionalInteger

object E90 extends Calculated[E90, CT600EBoxRetriever] with IncomeCalculator {
  override def calculate(boxRetriever: CT600EBoxRetriever): E90 = calculateTotalIncome(
    e50 = boxRetriever.e50(),
    e55 = boxRetriever.e55(),
    e60 = boxRetriever.e60(),
    e65 = boxRetriever.e65(),
    e70 = boxRetriever.e70(),
    e75 = boxRetriever.e75(),
    e80 = boxRetriever.e80(),
    e85 = boxRetriever.e85()
  )
}
