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

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.{AnnualInvestmentAllowanceCalculator, AnnualInvestmentAllowancePeriods}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever


case class CATO02(value: Int) extends CtBoxIdentifier(name = "Maximum Annual Investment Allowance") with CtInteger

object CATO02 extends Calculated[CATO02, ComputationsBoxRetriever] with AnnualInvestmentAllowanceCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CATO02 = {
    maximum(cp1 = fieldValueRetriever.cp1(),
            cp2 = fieldValueRetriever.cp2(),
            allowableAmounts = AnnualInvestmentAllowancePeriods())
  }
}
