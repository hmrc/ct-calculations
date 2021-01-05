/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.calculations

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.{AC3, AC4}

class PeriodOfAccountsCalculatorSpec extends WordSpec with Matchers with PeriodOfAccountsCalculator {

  "isLongPeriodOfAccounts" should {

    "return true for a PoA of 15 months" in {
      val poaStartDate = AC3(new LocalDate(2015, 4, 1))
      val poaEndDate = AC4(new LocalDate(2016, 7, 1))

      isLongPeriodOfAccounts(poaStartDate, poaEndDate) shouldBe true
    }

    "return true for a PoA of 12 months" in {
      val poaStartDate = AC3(new LocalDate(2015, 4, 1))
      val poaEndDate = AC4(new LocalDate(2016, 4, 1))

      isLongPeriodOfAccounts(poaStartDate, poaEndDate) shouldBe true
    }

    "return false for a PoA of 12 months minus one day" in {
      val poaStartDate = AC3(new LocalDate(2015, 4, 1))
      val poaEndDate = AC4(new LocalDate(2016, 3, 31))

      isLongPeriodOfAccounts(poaStartDate, poaEndDate) shouldBe false
    }

  }

}
