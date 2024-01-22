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

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{AnnualConstant, Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.ct600.calculations.AccountingPeriodHelper.{endingFinancialYear, startingFinancialYear}
import uk.gov.hmrc.ct.ct600.v2.B45
import uk.gov.hmrc.ct.ct600.v2.B45.rateOfTaxFy1
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

// was B45
case class B340(value: BigDecimal) extends CtBoxIdentifier(name = "First Rate Of Tax FY1") with AnnualConstant with CtBigDecimal

object B340 extends CorporationTaxCalculator with Calculated[B340, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B340 = {
    val accountingPeriod = HmrcAccountingPeriod(fieldValueRetriever.cp1(), fieldValueRetriever.cp2())
    val fy1: Int = startingFinancialYear(accountingPeriod.start)
    val fy2: Int = endingFinancialYear(accountingPeriod.end)
    B340(rateOfTaxFy1(HmrcAccountingPeriod(fieldValueRetriever.cp1(),fieldValueRetriever.cp2()),
      fieldValueRetriever.b335(),fieldValueRetriever.b620(),
      if (fy2 != fy1) fieldValueRetriever.b327() else fieldValueRetriever.b326() ))
  }
}
