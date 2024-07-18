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

import uk.gov.hmrc.ct.box.{Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.computations.superdeductions.{SuperDeductionPercentage, SuperDeductionPeriod}

case class CPSuperDeductionPercentage(value: BigDecimal) extends CtBoxIdentifier(name = "super deduction percentage") with CtBigDecimal

object CPSuperDeductionPercentage extends Calculated[CPSuperDeductionPercentage, ComputationsBoxRetriever] {
  override def calculate(boxRetriever: ComputationsBoxRetriever): CPSuperDeductionPercentage = {
    CPSuperDeductionPercentage(SuperDeductionPercentage(
      HmrcAccountingPeriod(boxRetriever.cp1(), boxRetriever.cp2()),
      SuperDeductionPeriod(boxRetriever.ac5(), boxRetriever.ac6())
    ).percentage)
  }
}


