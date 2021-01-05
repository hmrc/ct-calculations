/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.cato.filing.util

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.utils.DateImplicits._

class DateImplicitsSpec extends WordSpec with Matchers {

  "date operator" should {

    val DATE_2012_04_01 = new LocalDate(2012, 4, 1)
    val DATE_2012_04_02 = new LocalDate(2012, 4, 2)

    "< should behave as JodaTime isBefore" in {

      DATE_2012_04_01 < DATE_2012_04_02 shouldBe true
      DATE_2012_04_02 < DATE_2012_04_01 shouldBe false
      DATE_2012_04_02 < DATE_2012_04_02 shouldBe false
    }

    "<= should behave as JodaTime isBefore or isEqual" in {

      DATE_2012_04_01 <= DATE_2012_04_02 shouldBe true
      DATE_2012_04_02 <= DATE_2012_04_01 shouldBe false
      DATE_2012_04_02 <= DATE_2012_04_02 shouldBe true
    }

    "> should behave as JodaTime isAfter" in {

      DATE_2012_04_01 > DATE_2012_04_02 shouldBe false
      DATE_2012_04_02 > DATE_2012_04_01 shouldBe true
      DATE_2012_04_02 > DATE_2012_04_02 shouldBe false
    }

    ">= should behave as JodaTime isAfter or isEqual" in {

      DATE_2012_04_01 >= DATE_2012_04_02 shouldBe false
      DATE_2012_04_02 >= DATE_2012_04_01 shouldBe true
      DATE_2012_04_02 >= DATE_2012_04_02 shouldBe true
    }
  }

}
