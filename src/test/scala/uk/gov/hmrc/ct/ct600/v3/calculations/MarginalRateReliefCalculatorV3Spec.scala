package uk.gov.hmrc.ct.ct600.v3.calculations

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.CATO05
import uk.gov.hmrc.ct.CATO05.computeMarginalRateReliefV3
import uk.gov.hmrc.ct.computations.{CP1, CP2, HmrcAccountingPeriod}
import uk.gov.hmrc.ct.ct600.calculations.InvalidAccountingPeriodException
import uk.gov.hmrc.ct.ct600.v3.{B315, B326, B327, B328, B335, B385}

class MarginalRateReliefCalculatorV3Spec extends WordSpec with Matchers {

  "V3 MarginalRateReliefCaclculator input validation" should {

    "Fail validation for an AP where the end date is before the start date" in {
      an[InvalidAccountingPeriodException] should be thrownBy
        computeMarginalRateReliefV3(B315(70000), B335(40000), B385(30000), B326(0), B327(0), B328(0), HmrcAccountingPeriod(CP1(new LocalDate(2024, 9, 1)), CP2(new LocalDate(2024, 5, 31))))
      an[InvalidAccountingPeriodException] should be thrownBy
        computeMarginalRateReliefV3(B315(70000), B335(40000), B385(30000), B326(0), B327(0), B328(0), HmrcAccountingPeriod(CP1(new LocalDate(2024, 9, 1)), CP2(new LocalDate(2023, 1, 31))))
    }

    "Not fail validation for an AP of 365 days across multiple non-leap years" in {
      computeMarginalRateReliefV3(B315(70000), B335(40000), B385(30000), B326(0), B327(0), B328(0), HmrcAccountingPeriod(CP1(new LocalDate(2025, 3, 1)), CP2(new LocalDate(2026, 2, 28))))
      computeMarginalRateReliefV3(B315(70000), B335(40000), B385(30000), B326(0), B327(0), B328(0), HmrcAccountingPeriod(CP1(new LocalDate(2025, 4, 1)), CP2(new LocalDate(2026, 3, 31))))
      computeMarginalRateReliefV3(B315(70000), B335(40000), B385(30000), B326(0), B327(0), B328(0), HmrcAccountingPeriod(CP1(new LocalDate(2025, 10, 1)), CP2(new LocalDate(2026, 9, 30))))
    }

    "Fail validation for an AP of 365 days across multiple non-leap years" in {
      an[InvalidAccountingPeriodException] should be thrownBy
        computeMarginalRateReliefV3(B315(70000), B335(40000), B385(30000), B326(0), B327(0), B328(0), HmrcAccountingPeriod(CP1(new LocalDate(2025, 3, 1)), CP2(new LocalDate(2026, 3, 1))))
      an[InvalidAccountingPeriodException] should be thrownBy
        computeMarginalRateReliefV3(B315(70000), B335(40000), B385(30000), B326(0), B327(0), B328(0), HmrcAccountingPeriod(CP1(new LocalDate(2025, 4, 1)), CP2(new LocalDate(2026, 4, 1))))
      an[InvalidAccountingPeriodException] should be thrownBy
        computeMarginalRateReliefV3(B315(70000), B335(40000), B385(30000), B326(0), B327(0), B328(0), HmrcAccountingPeriod(CP1(new LocalDate(2025, 10, 1)), CP2(new LocalDate(2026, 10, 1))))
    }

    "Not fail validation for an AP of 366 days starting beginning of February on a leap year and ending at the end of January the following year" in {
      computeMarginalRateReliefV3(B315(70000), B335(40000), B385(30000), B326(0), B327(0), B328(0), HmrcAccountingPeriod(CP1(new LocalDate(2024, 2, 1)), CP2(new LocalDate(2025, 1, 31))))
    }

    "Not fail validation for short APs in a single financial year which is not a leap year" in {
      computeMarginalRateReliefV3(B315(70000), B335(40000), B385(30000), B326(0), B327(0), B328(0), HmrcAccountingPeriod(CP1(new LocalDate(2025, 9, 1)), CP2(new LocalDate(2025, 12, 31))))
      computeMarginalRateReliefV3(B315(70000), B335(40000), B385(30000), B326(0), B327(0), B328(0), HmrcAccountingPeriod(CP1(new LocalDate(2025, 12, 1)), CP2(new LocalDate(2026, 3, 1))))
      computeMarginalRateReliefV3(B315(70000), B335(40000), B385(30000), B326(0), B327(0), B328(0), HmrcAccountingPeriod(CP1(new LocalDate(2025, 3, 1)), CP2(new LocalDate(2025, 3, 31))))
      computeMarginalRateReliefV3(B315(70000), B335(40000), B385(30000), B326(0), B327(0), B328(0), HmrcAccountingPeriod(CP1(new LocalDate(2025, 1, 1)), CP2(new LocalDate(2025, 3, 31))))
      computeMarginalRateReliefV3(B315(70000), B335(40000), B385(30000), B326(0), B327(0), B328(0), HmrcAccountingPeriod(CP1(new LocalDate(2025, 9, 1)), CP2(new LocalDate(2025, 11, 30))))

    }

    "Fail validation for an AP which starts before 1st April 2012" in {
      an[InvalidAccountingPeriodException] should be thrownBy
        computeMarginalRateReliefV3(B315(70000), B335(40000), B385(30000), B326(0), B327(0), B328(0), HmrcAccountingPeriod(CP1(new LocalDate(2006, 10, 1)), CP2(new LocalDate(2007, 9, 30))))
    }
  }
  "MarginalReliefCalculator" should {
    "Satisfy basic calculation 1 from example in ticket DLS-6918" in{///24658
                                                    //total prof,    fy1,   fy2,          ACboth, ACFY1,   ACFY2,     Financial period
      val rateRelief = computeMarginalRateReliefV3(B315(100000),B335(100000), B385(0), B326(0), B327(0), B328(0), HmrcAccountingPeriod(CP1(new LocalDate(2024, 4, 1)), CP2(new LocalDate(2025, 3, 31))))
      rateRelief shouldBe CATO05(2250)
    }
    "Satisfy calculations for AP straddling commencement date" in {
                                                  //total prof,    fy1,       fy2,          ACboth, ACFY1,   ACFY2,     Financial period
      val rateRelief = computeMarginalRateReliefV3(B315(175000),B335(43151), B385(131849), B326(2), B327(2), B328(2), HmrcAccountingPeriod(CP1(new LocalDate(2023, 1, 1)), CP2(new LocalDate(2023, 12, 31))))
      rateRelief shouldBe CATO05(0)
    }
    "Satisfy calculations for short AP company" in {
      val rateRelief = computeMarginalRateReliefV3(B315(25000),B335(12363), B385(12637), B326(0), B327(0), B328(0), HmrcAccountingPeriod(CP1(new LocalDate(2024, 1, 1)), CP2(new LocalDate(2024, 6, 30))))
      rateRelief shouldBe CATO05(1494.86)
    }

    "Satisfy change in no of associated companies but no change in rates or thresholds" in {
      val rateRelief = computeMarginalRateReliefV3(B315(55000),B335(27424), B385(27576), B326(3), B327(3), B328(3), HmrcAccountingPeriod(CP1(new LocalDate(2023, 10, 1)), CP2(new LocalDate(2024, 9, 30))))
      rateRelief shouldBe CATO05(112.50)
    }

    "Return 0 if date is before new financial year 2023" in {
      val rateRelief = computeMarginalRateReliefV3(B315(175000),B335(43151), B385(131849), B326(2), B327(0), B328(0), HmrcAccountingPeriod(CP1(new LocalDate(2022, 1, 11)), CP2(new LocalDate(2023, 1, 10))))
      rateRelief shouldBe CATO05(0)
    }
  }
}
