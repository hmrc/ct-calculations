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

import uk.gov.hmrc.ct.box.{Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.ct600.v2.calculations.MarginalRateReliefCalculator
import uk.gov.hmrc.ct.ct600.v2.retriever.CT600BoxRetriever

case class CATO04(value: BigDecimal) extends CtBoxIdentifier("Marginal Rate Relief") with CtBigDecimal

object CATO04 extends Calculated[CATO04, CT600BoxRetriever] with MarginalRateReliefCalculator {

  override def calculate(boxRetriever: CT600BoxRetriever): CATO04 = {
    computeMarginalRateRelief(b37 = boxRetriever.b37(),
                              b44 = boxRetriever.b44(),
                              b54 = boxRetriever.b54(),
                              b38 = boxRetriever.b38(),
                              b39 = boxRetriever.b39(),
                              accountingPeriod = HmrcAccountingPeriod(boxRetriever.cp1(), boxRetriever.cp2()))
  }
}
