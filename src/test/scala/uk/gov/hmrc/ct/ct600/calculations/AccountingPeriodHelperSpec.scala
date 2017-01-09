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

package uk.gov.hmrc.ct.ct600.calculations

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.{CP1, CP2, HmrcAccountingPeriod}


class AccountingPeriodHelperSpec extends WordSpec with Matchers {

  "accountingPeriodDaysInFinancialYear" should {

    "return 365 for accounting period which falls exactly in a normal financial year" in {
      AccountingPeriodHelper.accountingPeriodDaysInFinancialYear(2014, HmrcAccountingPeriod(CP1(new LocalDate(2014, 4, 1)), CP2(new LocalDate(2015, 3, 31)))) shouldBe BigDecimal(365)
    }

    "return 366 for accounting period which falls exactly in a leap financial year" in {
      AccountingPeriodHelper.accountingPeriodDaysInFinancialYear(2015, HmrcAccountingPeriod(CP1(new LocalDate(2015, 4, 1)), CP2(new LocalDate(2016, 3, 31)))) shouldBe BigDecimal(366)
    }

    "return 31 for accounting period which spans July only" in {
      AccountingPeriodHelper.accountingPeriodDaysInFinancialYear(2014, HmrcAccountingPeriod(CP1(new LocalDate(2014, 7, 1)), CP2(new LocalDate(2014, 7, 31)))) shouldBe BigDecimal(31)
    }

    "return the correct days for each year when AP spans two years with a total that equals the full AP period" in {
      val fy1 = AccountingPeriodHelper.accountingPeriodDaysInFinancialYear(2014, HmrcAccountingPeriod(CP1(new LocalDate(2014, 9, 1)), CP2(new LocalDate(2015, 8, 31))))
      val fy2 = AccountingPeriodHelper.accountingPeriodDaysInFinancialYear(2015, HmrcAccountingPeriod(CP1(new LocalDate(2014, 9, 1)), CP2(new LocalDate(2015, 8, 31))))
      fy1 shouldBe 212
      fy2 shouldBe 153
      fy1 + fy2 shouldBe 365
    }

    "return the correct days for each year when AP spans two years with a total that equals the full AP period, where one of the years is a leap year" in {
      val fy1 = AccountingPeriodHelper.accountingPeriodDaysInFinancialYear(2015, HmrcAccountingPeriod(CP1(new LocalDate(2015, 9, 1)), CP2(new LocalDate(2016, 8, 31))))
      val fy2 = AccountingPeriodHelper.accountingPeriodDaysInFinancialYear(2016, HmrcAccountingPeriod(CP1(new LocalDate(2015, 9, 1)), CP2(new LocalDate(2016, 8, 31))))
      fy1 shouldBe 213
      fy2 shouldBe 153
      fy1 + fy2 shouldBe 366
    }
  }

  "accountingPeriodSpansTwoFinancialYears" should {

    "return false when AP falls in one financial year" in {
      AccountingPeriodHelper.accountingPeriodSpansTwoFinancialYears(HmrcAccountingPeriod(CP1(new LocalDate(2014, 4, 1)), CP2(new LocalDate(2015, 3, 31)))) shouldBe false
      AccountingPeriodHelper.accountingPeriodSpansTwoFinancialYears(HmrcAccountingPeriod(CP1(new LocalDate(2015, 4, 1)), CP2(new LocalDate(2016, 3, 31)))) shouldBe false
      AccountingPeriodHelper.accountingPeriodSpansTwoFinancialYears(HmrcAccountingPeriod(CP1(new LocalDate(2014, 5, 1)), CP2(new LocalDate(2014, 7, 31)))) shouldBe false
    }

    "return true when AP falls in two financial years" in {
      AccountingPeriodHelper.accountingPeriodSpansTwoFinancialYears(HmrcAccountingPeriod(CP1(new LocalDate(2014, 5, 1)), CP2(new LocalDate(2015, 4, 30)))) shouldBe true
      AccountingPeriodHelper.accountingPeriodSpansTwoFinancialYears(HmrcAccountingPeriod(CP1(new LocalDate(2014, 3, 1)), CP2(new LocalDate(2015, 2, 28)))) shouldBe true
      AccountingPeriodHelper.accountingPeriodSpansTwoFinancialYears(HmrcAccountingPeriod(CP1(new LocalDate(2015, 3, 1)), CP2(new LocalDate(2016, 2, 29)))) shouldBe true
    }
  }

  "financialYearDates" should {

    "return correct dates" in {
      val (startDate, endDate) = AccountingPeriodHelper.financialYearStartingIn(2014)
      startDate shouldBe new LocalDate(2014, 4, 1)
      endDate shouldBe new LocalDate(2015, 3, 31)
    }
  }

  "fallsInFinancialYear" should {

    "return 2013 for a date in 2014 before 1st April" in {
      AccountingPeriodHelper.fallsInFinancialYear(new LocalDate(2014, 1, 1)) shouldBe 2013
      AccountingPeriodHelper.fallsInFinancialYear(new LocalDate(2014, 3, 31)) shouldBe 2013
    }

    "return 2014 for a date in 2014 after 1st April" in {
      AccountingPeriodHelper.fallsInFinancialYear(new LocalDate(2014, 4, 1)) shouldBe 2014
      AccountingPeriodHelper.fallsInFinancialYear(new LocalDate(2014, 12, 31)) shouldBe 2014
    }
  }
}
