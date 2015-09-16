package uk.gov.hmrc.ct.computations.calculations

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.{CP1, CP2, HmrcAccountingPeriod}

class HmrcAccountingPeriodCalculatorSpec extends WordSpec with Matchers {

  val prepopStartDate = CP1(new LocalDate(2012,2,23))
  val prepopEndDate = CP2(new LocalDate(2012,7,26))
  val userStartDate = new LocalDate(2013,2,23)
  val userEndDate = new LocalDate(2013,7,26)

  "accountingPeriod" should {

    "prefer user entered start date to prepop start date" in new HmrcAccountingPeriodCalculator {

      val ap = accountingPeriod(HmrcAccountingPeriodParameters(HmrcAccountingPeriod(prepopStartDate, prepopEndDate), Some(userStartDate), None))

      ap.startDate shouldBe userStartDate
      ap.endDate shouldBe prepopEndDate.value
    }

    "prefer user entered end date to prepop end date" in new HmrcAccountingPeriodCalculator {
      val ap = accountingPeriod(HmrcAccountingPeriodParameters(HmrcAccountingPeriod(prepopStartDate, prepopEndDate), None, Some(userEndDate)))

      ap.startDate shouldBe prepopStartDate.value
      ap.endDate shouldBe userEndDate
    }
  }
}
