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

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.ct600.v3.calculations.MarginalRateReliefCalculatorV3
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

case class CATO05(value: BigDecimal) extends CtBoxIdentifier("Marginal Rate Relief") with CtBigDecimal


object CATO05 extends Calculated[CATO05, CT600BoxRetriever] with MarginalRateReliefCalculatorV3 {

  override def calculate(boxRetriever: CT600BoxRetriever): CATO05 = {
    computeMarginalRateReliefV3(b315 = boxRetriever.b315(),
      b335 = boxRetriever.b335(),
      b385 = boxRetriever.b385(),
      b326 = boxRetriever.b326(),
      b327 = boxRetriever.b327(),
      b328 = boxRetriever.b328(),
      b620 = boxRetriever.b620(),
      accountingPeriod = HmrcAccountingPeriod(boxRetriever.cp1(), boxRetriever.cp2()))
  }
}