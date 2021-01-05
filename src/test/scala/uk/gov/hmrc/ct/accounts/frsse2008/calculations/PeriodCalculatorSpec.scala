/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frsse2008.calculations

import org.joda.time.LocalDate
import org.scalatest.{WordSpec, Matchers}

class PeriodCalculatorSpec extends WordSpec with Matchers {

  "periodHeading" should {

    "return heading components containing multiple months" in {
      val startDate = new LocalDate(2014, 1, 1)
      val endDate = new LocalDate(2014, 7, 1)

      PeriodCalculator.periodHeadingComponents(startDate, endDate).monthCount shouldBe 6
      PeriodCalculator.periodHeadingComponents(startDate, endDate).messageKey shouldBe "periodHeader.plural"
      PeriodCalculator.periodHeadingComponents(startDate, endDate).dateText shouldBe "1 Jul 2014"
    }

    "return heading components containing a single month" in {
      val startDate = new LocalDate(2014, 1, 1)
      val endDate = new LocalDate(2014, 2, 1)

      PeriodCalculator.periodHeadingComponents(startDate, endDate).monthCount shouldBe 1
      PeriodCalculator.periodHeadingComponents(startDate, endDate).messageKey shouldBe "periodHeader.singular"
      PeriodCalculator.periodHeadingComponents(startDate, endDate).dateText shouldBe "1 Feb 2014"
    }

    "return heading components containing a partial month with only 15 days" in {
      val startDate = new LocalDate(2014, 1, 1)
      val endDate = new LocalDate(2014, 2, 15)

      PeriodCalculator.periodHeadingComponents(startDate, endDate).monthCount shouldBe 1
      PeriodCalculator.periodHeadingComponents(startDate, endDate).messageKey shouldBe "periodHeader.singular"
      PeriodCalculator.periodHeadingComponents(startDate, endDate).dateText shouldBe "15 Feb 2014"
    }

    "return heading components containing a partial month with only 16 days" in {
      val startDate = new LocalDate(2014, 1, 1)
      val endDate = new LocalDate(2014, 2, 16)

      PeriodCalculator.periodHeadingComponents(startDate, endDate).monthCount shouldBe 2
      PeriodCalculator.periodHeadingComponents(startDate, endDate).messageKey shouldBe "periodHeader.plural"
      PeriodCalculator.periodHeadingComponents(startDate, endDate).dateText shouldBe "16 Feb 2014"
    }

    "return heading components where the period is 12 months" in {
      val startDate = new LocalDate(2014, 1, 1)
      val endDate = new LocalDate(2015, 1, 1)

      PeriodCalculator.periodHeadingComponents(startDate, endDate).monthCount shouldBe 12
      PeriodCalculator.periodHeadingComponents(startDate, endDate).messageKey shouldBe ""
      PeriodCalculator.periodHeadingComponents(startDate, endDate).dateText shouldBe "2015"
    }

    "return heading components where the period is greater than 12 months" in {
      val startDate = new LocalDate(2014, 1, 1)
      val endDate = new LocalDate(2015, 4, 30)

      PeriodCalculator.periodHeadingComponents(startDate, endDate).monthCount shouldBe 16
      PeriodCalculator.periodHeadingComponents(startDate, endDate).messageKey shouldBe "periodHeader.plural"
      PeriodCalculator.periodHeadingComponents(startDate, endDate).dateText shouldBe "30 Apr 2015"
    }
  }

}
