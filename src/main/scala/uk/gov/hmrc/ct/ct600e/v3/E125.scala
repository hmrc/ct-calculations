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
import uk.gov.hmrc.ct.ct600e.v3.calculations.ExpenditureCalculator
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

case class E125(value: Option[Int]) extends CtBoxIdentifier("Expenditure: Total of boxes E95 to E120") with CtOptionalInteger

object E125 extends Calculated[E125, CT600EBoxRetriever] with ExpenditureCalculator {
  override def calculate(boxRetriever: CT600EBoxRetriever): E125 = {
    calculateTotalExpenditure(
      e95 = boxRetriever.e95(),
      e100 = boxRetriever.e100(),
      e105 = boxRetriever.e105(),
      e110 = boxRetriever.e110(),
      e115 = boxRetriever.e115(),
      e120 = boxRetriever.e120()
    )
  }
}
