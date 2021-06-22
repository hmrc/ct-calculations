package uk.gov.hmrc.ct.computations.losses

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.computations.losses
import uk.gov.hmrc.ct.utils.UnitSpec

class extendedCarryBackLossesSpec extends UnitSpec {
  "The Extended Carry Back Loss Period" should {
    "be false for end date before 01 April 2020" in {
      val startDate = new LocalDate("2019-04-01")
      val endDate = new LocalDate("2020-03-31")

      val result = losses.doesPeriodCoverECBL(endDate)
      result shouldBe false
    }

    "be true for end date on 01 April 2020" in {
      val startDate = new LocalDate("2019-03-31")
      val endDate = new LocalDate("2020-04-01")

      val result = losses.doesPeriodCoverECBL(endDate)
      result shouldBe true
    }

    "be true for period with end date landing between 01 April 2020 and 31 March 2022" in {
      val startDate = new LocalDate("2021-01-01")
      val endDate = new LocalDate("2021-12-31")

      val result = losses.doesPeriodCoverECBL(endDate)
      result shouldBe true
    }

    "be false for end date after 31 March 2022" in {
      val startDate = new LocalDate("2021-04-01")
      val endDate = new LocalDate("2022-04-01")

      val result = losses.doesPeriodCoverECBL(endDate)
      result shouldBe false
    }
  }
}
