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

package uk.gov.hmrc.ct.ct600.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.ct600.v3.B350.financialYearForDate

package object associatedCompanies
{

  val multipleTaxRateV3StartDate = LocalDate.parse("2023-03-31")

  def doesPeriodCoverTwoFinancialYears(startDate: LocalDate, endDate: LocalDate) = {
    val fy1 = financialYearForDate(startDate)
    val fy2 = financialYearForDate(endDate)
    if (fy1 != fy2) {
      true
    } else {
      false
    }
  }
  def splitFincialYearForHelpText(startDate: LocalDate, endDate: LocalDate) = {
    if (startDate.getMonthOfYear < 4) {
      startDate.getYear.toString
    } else {
      endDate.getYear.toString
    }
  }
  def doesfilingperiodcoversafter2023(endDate: LocalDate) = {
    if(endDate.isAfter(multipleTaxRateV3StartDate)) {
      true
    } else {
      false
    }
  }
}
