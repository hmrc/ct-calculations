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
