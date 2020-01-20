package uk.gov.hmrc.ct.computations.calculations

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}

class SBACalculatorSpec extends WordSpec with Matchers {

  "SBA calculator" should {
    "apportion and calculate the right amount of sba claimable for a building whose AP is in the first 6 months during a leap year" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2020-01-01")
      val endDate: LocalDate = new LocalDate("2020-6-30")
      val cost: Int = 10000
      val contractStartDate: LocalDate = new LocalDate("2020-01-01")

      override val rate: BigDecimal = 0.02

      val result = getAmountClaimableForSBA(apStartDate, endDate, contractStartDate, cost)

      result shouldBe 99
    }

    "apportion and calculate the right amount of sba claimable for a building for 12 months during a leap year" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2020-01-01")
      val endDate: LocalDate = new LocalDate("2020-12-31")
      val cost: Int = 10000
      val contractStartDate: LocalDate = new LocalDate("2020-01-01")

      override val rate: BigDecimal = 0.02

      val result = getAmountClaimableForSBA(apStartDate, endDate, contractStartDate, cost)

      result shouldBe 200
    }

    "apportion and calculate the right amount of sba claimable for a building for 3 months during a normal year where the contract date starts after AP start date." in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2020-01-01")
      val endDate: LocalDate = new LocalDate("2020-12-31")
      val cost: Int = 10000
      val contractStartDate: LocalDate = new LocalDate("2020-10-01")

      override val rate: BigDecimal = 0.02

      val result = getAmountClaimableForSBA(apStartDate, endDate, contractStartDate, cost)

      result shouldBe 50
    }

    "apportion and calculate the right amount of sba claimable for a building for 3 months during a leap year where the contract date starts after AP and start date including february" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2020-01-01")
      val endDate: LocalDate = new LocalDate("2020-12-31")
      val cost: Int = 10000
      val contractStartDate: LocalDate = new LocalDate("2020-02-01")

      override val rate: BigDecimal = 0.02

      val result = getAmountClaimableForSBA(apStartDate, endDate, contractStartDate, cost)

      result shouldBe 183
    }


    "apportion and calculate the right amount of sba claimable for a building for 3 months during a regular year where the contract date starts after AP start date including february" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2021-01-01")
      val endDate: LocalDate = new LocalDate("2021-12-31")
      val cost: Int = 10000
      val contractStartDate: LocalDate = new LocalDate("2021-02-01")

      override val rate: BigDecimal = 0.02

      val result = getAmountClaimableForSBA(apStartDate, endDate, contractStartDate, cost)

      result shouldBe 183
    }

    "apportion and calculate the right amount of sba claimable for a building for 3 months during a regular year where the contract date starts after AP start date not including february" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2021-01-01")
      val endDate: LocalDate = new LocalDate("2021-12-31")
      val cost: Int = 10000
      val contractStartDate: LocalDate = new LocalDate("2021-03-01")

      override val rate: BigDecimal = 0.02

      val result = getAmountClaimableForSBA(apStartDate, endDate, contractStartDate, cost)

      result shouldBe 168
    }
  }
}
