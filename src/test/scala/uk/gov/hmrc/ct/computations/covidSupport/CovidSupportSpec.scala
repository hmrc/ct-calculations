package uk.gov.hmrc.ct.computations.covidSupport

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.computations.covidSupport
import uk.gov.hmrc.ct.utils.UnitSpec

class CovidSupportSpec extends UnitSpec {
  "COVID" should {
    "be false for end date before March 2020" in {
      val startDate = new LocalDate("2019-12-31")
      val endDate = new LocalDate("2020-02-29")

      val result = covidSupport.doesPeriodCoverCovid(startDate, endDate)
      result shouldBe false
    }

    "be true for end date after March 2020" in {
      val startDate = new LocalDate("2019-12-31")
      val endDate = new LocalDate("2020-03-01")

      val result = covidSupport.doesPeriodCoverCovid(startDate, endDate)
      result shouldBe true
    }
  }

  "EOTHO" should {
    "be false for end date before 03 August 2020" in {
      val startDate = new LocalDate("2019-12-31")
      val endDate = new LocalDate("2020-08-02")

      val result = covidSupport.doesPeriodCoverEotho(startDate, endDate)
      result shouldBe false
    }

    "be true for end date on 03 August 2020" in {
      val startDate = new LocalDate("2019-12-31")
      val endDate = new LocalDate("2020-08-03")

      val result = covidSupport.doesPeriodCoverEotho(startDate, endDate)
      result shouldBe true
    }

    "be true for period during August 2020" in {
      val startDate = new LocalDate("2020-01-01")
      val endDate = new LocalDate("2020-12-31")

      val result = covidSupport.doesPeriodCoverEotho(startDate, endDate)
      result shouldBe true
    }

    "be true for start date before 01 August 2020" in {
      val startDate = new LocalDate("2020-08-31")
      val endDate = new LocalDate("2020-12-31")

      val result = covidSupport.doesPeriodCoverEotho(startDate, endDate)
      result shouldBe true
    }


    "be false for start date on 01 September 2020" in {
      val startDate = new LocalDate("2020-09-01")
      val endDate = new LocalDate("2020-12-31")

      val result = covidSupport.doesPeriodCoverEotho(startDate, endDate)
      result shouldBe false
    }
  }
}
