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

package uk.gov.hmrc.ct.ct600.v2.calculations

import org.joda.time.LocalDate
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.prop.Tables.Table
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.CATO04
import uk.gov.hmrc.ct.computations.{CP1, CP2, CP295, HmrcAccountingPeriod}
import uk.gov.hmrc.ct.ct600.calculations.{CorporationTaxCalculatorParameters, InvalidAccountingPeriodException}
import uk.gov.hmrc.ct.ct600.v2.{B39, B38, B37}

class MarginalRateReliefCalculatorSpec extends WordSpec with Matchers {

  "MarginalRateReliefCalculator input validation" should {

    "Fail validation for an AP where the end date is before the start date" in new Calculator {
      an[InvalidAccountingPeriodException] should be thrownBy
        calculateMRR(B37(300001), B38(None), B39(None), HmrcAccountingPeriod(CP1(new LocalDate(2014, 9, 1)), CP2(new LocalDate(2014, 5, 31))))

      an[InvalidAccountingPeriodException] should be thrownBy
        calculateMRR(B37(300001), B38(None), B39(None), HmrcAccountingPeriod(CP1(new LocalDate(2014, 9, 1)), CP2(new LocalDate(2013, 1, 31))))
    }

    "Not fail validation for an AP of 365 days across multiple non-leap years" in new Calculator {
      calculateMRR(B37(300001), B38(None), B39(None), HmrcAccountingPeriod(CP1(new LocalDate(2013, 3, 1)), CP2(new LocalDate(2014, 2, 28))))
      calculateMRR(B37(300001), B38(None), B39(None), HmrcAccountingPeriod(CP1(new LocalDate(2013, 4, 1)), CP2(new LocalDate(2014, 3, 31))))
      calculateMRR(B37(300001), B38(None), B39(None), HmrcAccountingPeriod(CP1(new LocalDate(2013, 10, 1)), CP2(new LocalDate(2014, 9, 30))))
    }

    "Fail validation for an AP of 365 days across multiple non-leap years" in new Calculator {
      an[InvalidAccountingPeriodException] should be thrownBy
        calculateMRR(B37(300001), B38(None), B39(None), HmrcAccountingPeriod(CP1(new LocalDate(2013, 3, 1)), CP2(new LocalDate(2014, 3, 1))))
      an[InvalidAccountingPeriodException] should be thrownBy
        calculateMRR(B37(300001), B38(None), B39(None), HmrcAccountingPeriod(CP1(new LocalDate(2013, 4, 1)), CP2(new LocalDate(2014, 4, 1))))
      an[InvalidAccountingPeriodException] should be thrownBy
        calculateMRR(B37(300001), B38(None), B39(None), HmrcAccountingPeriod(CP1(new LocalDate(2013, 10, 1)), CP2(new LocalDate(2014, 10, 1))))
    }

    "Not fail validation for an AP of 366 days starting beginning of February on a leap year and ending at the end of January the following year" in new Calculator {
      calculateMRR(B37(300001), B38(None), B39(None), HmrcAccountingPeriod(CP1(new LocalDate(2012, 2, 1)), CP2(new LocalDate(2013, 1, 31))))
    }

    "Not fail validation for short APs in a single financial year which is not a leap year" in new Calculator {
      calculateMRR(B37(300001), B38(None), B39(None), HmrcAccountingPeriod(CP1(new LocalDate(2014, 9, 1)), CP2(new LocalDate(2014, 12, 31))))
      calculateMRR(B37(300001), B38(None), B39(None), HmrcAccountingPeriod(CP1(new LocalDate(2013, 12, 1)), CP2(new LocalDate(2014, 3, 1))))
      calculateMRR(B37(300001), B38(None), B39(None), HmrcAccountingPeriod(CP1(new LocalDate(2014, 3, 1)), CP2(new LocalDate(2014, 3, 31))))
      calculateMRR(B37(300001), B38(None), B39(None), HmrcAccountingPeriod(CP1(new LocalDate(2014, 1, 1)), CP2(new LocalDate(2014, 3, 31))))
      calculateMRR(B37(300001), B38(None), B39(None), HmrcAccountingPeriod(CP1(new LocalDate(2014, 9, 1)), CP2(new LocalDate(2014, 11, 30))))
    }

    "Fail validation for an AP which starts before 1st April 2012" in new Calculator {
      an[InvalidAccountingPeriodException] should be thrownBy
        calculateMRR(B37(300001), B38(None), B39(None), HmrcAccountingPeriod(CP1(new LocalDate(2006, 10, 1)), CP2(new LocalDate(2007, 9, 30))))
    }
  }

  val jiraCalculationCATO1581Examples = Table(
    ("b37BasicProfitChargeable", "cp1AccountingPeriodStartDate", "cp2AccountingPeriodEndDate", "b38FrankedInvestment", "cato04MRR"),
    (300000, "2013-04-01", "2014-03-31", 0, 0.00),
    (300001, "2013-04-01", "2014-03-31", 0, 8999.99),
    (400000, "2013-04-01", "2014-03-31", 0, 8250.00),
    (1000000, "2013-04-01", "2014-03-31", 0, 3750.00),
    (1400000, "2013-04-01", "2014-03-31", 0, 750.00),
    (1499999, "2013-04-01", "2014-03-31", 0, 0.01),
    (1500000, "2013-11-01", "2014-10-1", 0, 0.00),
    (300000, "2014-1-01", "2014-3-31", 0, 523.97),
    (300001, "2014-1-01", "2014-3-31", 0, 523.97),
    (400000, "2014-1-01", "2014-3-31", 0, 0.00),
    (1000000, "2014-1-01", "2014-3-31", 0, 0.00),
    (1400000, "2014-1-01", "2014-3-31", 0, 0.00),
    (1499999, "2014-1-01", "2014-3-31", 0, 0.00),
    (1500000, "2014-1-01", "2014-3-31", 0, 0.00),
    (200000, "2013-11-01", "2014-10-1", 0, 0.00),
    (300000, "2013-11-01", "2014-10-1", 0, 5118.40),
    (400000, "2013-11-01", "2014-10-1", 0, 4643.03),
    (1000000, "2013-11-01", "2014-10-1", 0, 1790.79),
    (1400000, "2013-11-01", "2014-10-1", 0, 0.00),
    (1499999, "2013-11-01", "2014-10-1", 0, 0.00),
    (1500000, "2013-11-01", "2014-10-1", 0, 0.00),
    (300000, "2013-04-01", "2014-03-31", 500, 8981.28),
    (300001, "2013-04-01", "2014-03-31", 500, 8981.27),
    (299500, "2013-04-01", "2014-03-31", 500, 0.00),
    (299501, "2013-04-01", "2014-03-31", 500, 8984.99),
    (400000, "2013-04-01", "2014-03-31", 500, 8235.96),
    (1000000, "2013-04-01", "2014-03-31", 500, 3744.38),
    (1400000, "2013-04-01", "2014-03-31", 500, 745.98),
    (1499999, "2013-04-01", "2014-03-31", 500, 0.00),
    (1500000, "2013-04-01", "2014-03-31", 500, 0.00),
    (300000, "2014-01-01", "2014-03-31", 500, 519.36),
    (300001, "2014-01-01", "2014-03-31", 500, 519.35),
    (400000, "2014-01-01", "2014-03-31", 500, 0.00),
    (1000000, "2014-01-01", "2014-03-31", 500, 0.00),
    (1400000, "2014-01-01", "2014-03-31", 500, 0.00),
    (1499999, "2014-01-01", "2014-03-31", 500, 0.00),
    (1500000, "2014-01-01", "2014-03-31", 500, 0.00),
    (200000, "2013-11-01", "2014-10-1", 500, 0.00),
    (300000, "2013-11-01", "2014-10-1", 500, 5107.51),
    (400000, "2013-11-01", "2014-10-1", 500, 4634.86),
    (1000000, "2013-11-01", "2014-10-1", 500, 1787.52),
    (1400000, "2013-11-01", "2014-10-1", 500, 0.00),
    (1499999, "2013-11-01", "2014-10-1", 500, 0.00),
    (1500000, "2013-11-01", "2014-10-1", 500, 0.00)
  )

  val jiraCalculationCATO1672Examples = Table(
    ("b37BasicProfitChargeable", "cp1AccountingPeriodStartDate", "cp2AccountingPeriodEndDate", "b38FrankedInvestment", "cato04MRR"),
    (300000, "2006-10-02", "2007-10-01", 0, 0.00),
    (300001, "2006-10-02", "2007-10-01", 0, 31487.65),
    (400000, "2006-10-02", "2007-10-01", 0, 28863.70),
    (1000000, "2006-10-02", "2007-10-01", 0, 13119.86),
    (1400000, "2006-10-02", "2007-10-01", 0, 2623.97),
    (1499999, "2006-10-02", "2007-10-01", 0, 0.03),
    (1500000, "2006-10-02", "2007-10-01", 0, 0.00),

    (300000, "2007-07-01", "2008-06-30", 0, 0.00),
    (300001, "2007-07-01", "2008-06-30", 0, 27762.28),
    (400000, "2007-07-01", "2008-06-30", 0, 25448.76),
    (1000000, "2007-07-01", "2008-06-30", 0, 11567.62),
    (1400000, "2007-07-01", "2008-06-30", 0, 2313.52),
    (1499999, "2007-07-01", "2008-06-30", 0, 0.02),
    (1500000, "2007-07-01", "2008-06-30", 0, 0.00),

    (300000, "2009-07-01", "2010-06-30", 0, 0.00),
    (300001, "2009-07-01", "2010-06-30", 0, 20999.98),
    (400000, "2009-07-01", "2010-06-30", 0, 19250.00),
    (1000000, "2009-07-01", "2010-06-30", 0, 8750.00),
    (1400000, "2009-07-01", "2010-06-30", 0, 1750.00),
    (1499999, "2009-07-01", "2010-06-30", 0, 0.02),
    (1500000, "2009-07-01", "2010-06-30", 0, 0.00),

    (300000, "2011-07-01", "2012-06-30", 0, 0.00),
    (300001, "2011-07-01", "2012-06-30", 0, 16508.19),
    (400000, "2011-07-01", "2012-06-30", 0, 15132.51),
    (1000000, "2011-07-01", "2012-06-30", 0, 6878.42),
    (1400000, "2011-07-01", "2012-06-30", 0, 1375.68),
    (1499999, "2011-07-01", "2012-06-30", 0, 0.01),
    (1500000, "2011-07-01", "2012-06-30", 0, 0.00),


    (300000, "2013-07-01", "2014-06-30", 0, 0.00),
    (300001, "2013-07-01", "2014-06-30", 0, 7504.10),
    (400000, "2013-07-01", "2014-06-30", 0, 6878.77),
    (1000000, "2013-07-01", "2014-06-30", 0, 3126.71),
    (1400000, "2013-07-01", "2014-06-30", 0, 625.34),
    (1499999, "2013-07-01", "2014-06-30", 0, 0.01),
    (1500000, "2013-07-01", "2014-06-30", 0, 0.00)
  )


  val jiraCalculationCATO1670Examples = Table(
    ("b37BasicProfitChargeable", "cp1AccountingPeriodStartDate", "cp2AccountingPeriodEndDate", "b38FrankedInvestment", "b39AssociatedCompanies", "cato04MRR"),
    (82475, "2013-01-01", "2013-12-31", 0, 3, 2374.26),
    (82475, "2013-01-01", "2013-12-31", 0, 0, 0.00),
    (82475, "2013-01-01", "2013-12-31", 0, 2, 0.00),
    (282475, "2013-01-01", "2013-12-31", 0, 3, 750.97),
    (282475, "2013-01-01", "2013-12-31", 0, 2, 1765.53),
    (282475, "2013-01-01", "2013-12-31", 20000, 2, 1497.19),

    (410000, "2014-04-01", "2015-03-31", 0, 0, 2725.00),
    (221000, "2014-04-01", "2015-03-31", 0, 0, 0.00),
    (30000, "2014-04-01", "2015-03-31", 50000, 2, 0.00),
    (80000, "2014-04-01", "2015-03-31", 50000, 2, 569.23),
    (100000, "2014-04-01", "2015-03-31", 410000, 2, 0.00),
    (90000, "2014-04-01", "2015-03-31", 0, 3, 712.50),
    (90000, "2014-04-01", "2015-03-31", 0, 2, 0.00),
    (280000, "2014-04-01", "2015-03-31", 35000, 0, 2633.33),
    (240000, "2014-04-01", "2015-03-31", 49999, 0, 0.00),
    (221000, "2014-06-01", "2015-05-31", 0, 0, 0.00),
    (30000, "2014-07-01", "2015-06-30", 50000, 2, 0.00),

    (100000, "2014-09-01", "2015-08-31", 410000, 2, 0.00),
    (90000, "2014-12-01", "2015-11-30", 0, 2, 0.00),
    (240000, "2014-03-01", "2015-02-28", 49999, 0, 0.00),
    (1650, "2014-04-01", "2014-04-02", 0, 0, 16.42),
    (50135, "2015-03-01", "2015-04-30", 0, 0, 0.00),
    (10000, "2014-05-01", "2014-06-30", 6250, 2, 0.00),

    (294000, "2015-03-01", "2015-09-30", 0, 2, 0.00),
    (44125, "2014-05-01", "2014-11-30", 0, 3, 439.35),
    (225343, "2014-05-01", "2015-03-31", 49999, 0, 0.00)
  )

  val jiraCalculationCATO2238Examples = Table(
    ("b37BasicProfitChargeable", "cp1AccountingPeriodStartDate", "cp2AccountingPeriodEndDate", "b38FrankedInvestment", "b39AssociatedCompanies", "cato04MRR"),
    (410000, "2014-04-01", "2015-03-31", 0, 0, 2725.00),
    (1000000, "2014-09-01", "2015-08-31", 0, 0, 726.03),
    (410000, "2014-05-01", "2015-04-30", 0, 0, 2501.02),
    (80000, "2014-08-01", "2015-07-31", 50000, 2, 378.97),
    (100000, "2015-04-01", "2016-03-31", 0, 0, 0.00)
  )


    "MarginalRateReliefCalculator" should {
    "satisfy calculations example provided in jira CATO-1581" in new Calculator {
      forAll(jiraCalculationCATO1581Examples) {
        (b37Value: Int,
         cp1Value: String,
         cp2Value: String,
         b38Value: Int,
         cato04Value: Double) =>
          calculateMRR(
            b37 = B37(b37Value),
            b38 = B38(Some(b38Value)),
            b39 = B39(None),
            accountingPeriod = HmrcAccountingPeriod(CP1(new LocalDate(cp1Value)), CP2(new LocalDate(cp2Value)))
          ) should be(CATO04(cato04Value))

      }
    }

    "satisfy calculations example provided in jira CATO-1672 for years 2006 to 2014" in new Calculator {
      forAll(jiraCalculationCATO1672Examples) {
        (b37Value: Int,
         cp1Value: String,
         cp2Value: String,
         b38Value: Int,
         cato04Value: Double) =>
          calculateMRR(
            b37 = B37(b37Value),
            b38 = B38(Some(b38Value)),
          b39 = B39(None),
            accountingPeriod = HmrcAccountingPeriod(CP1(new LocalDate(cp1Value)), CP2(new LocalDate(cp2Value)))
          ) should be(CATO04(cato04Value))

      }
    }

    "satisfy calculations example provided in jira CATO-1670" in new Calculator {
      forAll(jiraCalculationCATO1670Examples) {
        (b37Value: Int,
         cp1Value: String,
         cp2Value: String,
         b38Value: Int,
         b39Value: Int,
         cato04Value: Double) =>
          calculateMRR(
            b37 = B37(b37Value),
            b38 = B38(Some(b38Value)),
            b39 = B39(Option(b39Value)),
            accountingPeriod = HmrcAccountingPeriod(CP1(new LocalDate(cp1Value)), CP2(new LocalDate(cp2Value)))
          ) should be(CATO04(cato04Value))
      }
    }
  }

  "satisfy calculations example provided in jira CATO-2238" in new Calculator {
    forAll(jiraCalculationCATO2238Examples) {
      (b37Value: Int,
       cp1Value: String,
       cp2Value: String,
       b38Value: Int,
       b39Value: Int,
       cato04Value: Double) =>
        calculateMRR(
          b37 = B37(b37Value),
          b38 = B38(Some(b38Value)),
          b39 = B39(Option(b39Value)),
          accountingPeriod = HmrcAccountingPeriod(CP1(new LocalDate(cp1Value)), CP2(new LocalDate(cp2Value)))
        ) should be(CATO04(cato04Value))
    }
  }
}

trait Calculator extends MarginalRateReliefCalculator with CorporationTaxCalculator {

  def calculateMRR(b37: B37, b38: B38, b39: B39, accountingPeriod: HmrcAccountingPeriod): CATO04 = {
    val b44 = calculateApportionedProfitsChargeableFy1(CorporationTaxCalculatorParameters(CP295(b37.value), accountingPeriod))
    val b54 = calculateApportionedProfitsChargeableFy2(CorporationTaxCalculatorParameters(CP295(b37.value), accountingPeriod))
    computeMarginalRateRelief(b37, b44 = b44, b54 = b54, b38 = b38, b39 = b39, accountingPeriod = accountingPeriod)
  }
}
