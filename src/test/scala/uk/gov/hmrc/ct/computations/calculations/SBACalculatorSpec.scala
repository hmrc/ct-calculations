package uk.gov.hmrc.ct.computations.calculations

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.{CP1, CP2}

class SBACalculatorSpec extends WordSpec with Matchers {

  "SBA calculator" should {
    "apportion and calculate the right amount of sba claimable for a building for 6 months during a leap year" in new SBACalculator {

      val apStartDate: CP1 = CP1(new LocalDate("2020-01-01"))
      val endDate: CP2 = CP2(new LocalDate("2020-6-30"))
      val cost: Int = 10000
      val contractStartDate: LocalDate = new LocalDate("2020-01-01")

      override val rate: BigDecimal = 0.02

      daysInyear(apStartDate.value) shouldBe 366

      val result = getAmountClaimableForSBA(apStartDate, endDate, contractStartDate, cost)

      result shouldBe 99
    }

    "apportion and calculate the right amount of sba claimable for a building for 12 months during a leap year" in new SBACalculator {

      val apStartDate: CP1 = CP1(new LocalDate("2020-01-01"))
      val endDate: CP2 = CP2(new LocalDate("2020-12-31"))
      val cost: Int = 10000
      val contractStartDate: LocalDate = new LocalDate("2020-01-01")

      override val rate: BigDecimal = 0.02

      daysInyear(apStartDate.value) shouldBe 366

      val result = getAmountClaimableForSBA(apStartDate, endDate, contractStartDate, cost)

      result shouldBe 200
    }

    "apportion and calculate the right amount of sba claimable for a building for 3 months during a normal year where the contract date starts after AP start date." in new SBACalculator {

      val apStartDate: CP1 = CP1(new LocalDate("2020-01-01"))
      val endDate: CP2 = CP2(new LocalDate("2020-12-31"))
      val cost: Int = 10000
      val contractStartDate: LocalDate = new LocalDate("2020-11-01")

      override val rate: BigDecimal = 0.02

      daysInyear(apStartDate.value) shouldBe 366

      val result = getAmountClaimableForSBA(apStartDate, endDate, contractStartDate, cost)

      result shouldBe 200
    }
  }
}
