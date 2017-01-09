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

package uk.gov.hmrc.ct.computations.calculations

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.CATO03
import uk.gov.hmrc.ct.computations._


class WritingDownAllowanceCalculatorSpec extends WordSpec with Matchers {

  "WritingDownAllowanceCalculator" should {

    "return the written down allowance limit when pool is greater than small pools allowance" in new WritingDownAllowanceCalculator {
      pool(cp78 = CP78(5000),
           cp82 = CP82(2000),
           cp84 = CP84(0),
           cp88 = CP88(0)) should be > smallPoolsAllowance(cp1 = CP1(LocalDate.parse("2013-01-01")), cp2 = CP2(LocalDate.parse("2013-12-31")))
      val result = calculate(cp1 = CP1(LocalDate.parse("2013-01-01")), cp2 = CP2(LocalDate.parse("2013-12-31")),
                             cp78 = CP78(5000),
                             cp82 = CP82(2000),
                             cp84 = CP84(0),
                             cp88 = CP88(0))
      result shouldBe CATO03(writtenDownAllowanceLimit(cp1 = CP1(LocalDate.parse("2013-01-01")),
                                                       cp2 = CP2(LocalDate.parse("2013-12-31")),
                                                       cp78 = CP78(5000),
                                                       cp82 = CP82(2000),
                                                       cp84 = CP84(0),
                                                       cp88 = CP88(0)))
    }

    "return the written down allowance limit when pool is greater than small pools allowance for CATO-885" in new WritingDownAllowanceCalculator {

      val result = calculate(cp1 = CP1(LocalDate.parse("2013-04-01")),
        cp2 = CP2(LocalDate.parse("2014-01-27")),
        cp78 = CP78(831),
        cp82 = CP82(0),
        cp84 = CP84(0),
        cp88 = CP88(0))
      result shouldBe CATO03(124)
    }

    "return pool when pool is less than small pools allowance" in new WritingDownAllowanceCalculator {
      pool(cp78 = CP78(300),
           cp82 = CP82(600),
           cp84 = CP84(0),
           cp88 = CP88(0)) should be < smallPoolsAllowance(cp1 = CP1(LocalDate.parse("2013-01-01")),
                                                           cp2 = CP2(LocalDate.parse("2013-12-31")))

      val result = calculate(cp1 = CP1(LocalDate.parse("2013-01-01")),
                             cp2 = CP2(LocalDate.parse("2013-12-31")),
                             cp78 = CP78(300),
                             cp82 = CP82(600),
                             cp84 = CP84(0),
                             cp88 = CP88(0))

      result shouldBe CATO03(pool(cp78 = CP78(300),
                                  cp82 = CP82(600),
                                  cp84 = CP84(0),
                                  cp88 = CP88(0)))
    }
    "return pool when pool is equal to small pools allowance" in new WritingDownAllowanceCalculator {
      pool(cp78 = CP78(1000),
           cp82 = CP82(0),
           cp84 = CP84(0),
           cp88 = CP88(0)) shouldBe smallPoolsAllowance(cp1 = CP1(LocalDate.parse("2013-01-01")),
                                                        cp2 = CP2(LocalDate.parse("2013-12-31")))

      val result = calculate(cp1 = CP1(LocalDate.parse("2013-01-01")),
                             cp2 = CP2(LocalDate.parse("2013-12-31")),
                             cp78 = CP78(1000),
                             cp82 = CP82(0),
                             cp84 = CP84(0),
                             cp88 = CP88(0))

      result shouldBe CATO03(pool(cp78 = CP78(1000),
                                  cp82 = CP82(0),
                                  cp84 = CP84(0),
                                  cp88 = CP88(0)))
    }
  }

  "Parameters pool" should {
    "minus zero if disposal is None" in new WritingDownAllowanceCalculator {
      pool(cp78 = CP78(5000),
           cp82 = CP82(2000),
           cp84 = CP84(0),
           cp88 = CP88(0)) shouldBe 7000
    }
    "minus disposal if disposal is Some(x)" in new WritingDownAllowanceCalculator {
      pool(cp78 = CP78(5000),
           cp82 = CP82(2000),
           cp84 = CP84(3000),
           cp88 = CP88(0)) shouldBe 4000
    }
    "minus calculation of annual investment allowance if value returned by AIA" in new WritingDownAllowanceCalculator {
      pool(cp78 = CP78(5000),
           cp82 = CP82(2000),
           cp84 = CP84(3000),
           cp88 = CP88(1000)) shouldBe 3000
    }
    "pool is zero when result is negative" in new WritingDownAllowanceCalculator {
      pool(cp78 = CP78(0),
           cp82 = CP82(0),
           cp84 = CP84(3000),
           cp88 = CP88(1000)) shouldBe 0
    }
  }

  "Parameters small pools allowance (SPA)" should {
    "be apportioned for shorter than year 1/1/2011 -> 30/11/2011" in new WritingDownAllowanceCalculator {
      smallPoolsAllowance(cp1 = CP1(LocalDate.parse("2011-01-01")), cp2 = CP2(LocalDate.parse("2011-11-30"))) shouldBe 920
    }
    "be apportioned for leap year 1/1/2012 -> 31/12/2012" in new WritingDownAllowanceCalculator {
      smallPoolsAllowance(cp1 = CP1(LocalDate.parse("2012-01-01")), cp2 = CP2(LocalDate.parse("2012-12-31"))) shouldBe 1000
    }
  }

  "Parameters written down allowance limit (WDA)" should {
    "calculate for pool = 1700, accounting period less than a year" in new WritingDownAllowanceCalculator {
      writtenDownAllowanceLimit(cp1 = CP1(LocalDate.parse("2013-01-01")),
                                cp2 = CP2(LocalDate.parse("2013-06-30")),
                                cp78 = CP78(1000),
                                cp82 = CP82(1000),
                                cp84 = CP84(0),
                                cp88 = CP88(300)) shouldBe 153
    }
    "calculate for pool = 1700, accounting period of normal year" in new WritingDownAllowanceCalculator {
      writtenDownAllowanceLimit(cp1 = CP1(LocalDate.parse("2013-01-01")),
                                cp2 = CP2(LocalDate.parse("2013-12-31")),
                                cp78 = CP78(1000),
                                cp82 = CP82(1000),
                                cp84 = CP84(0),
                                cp88 = CP88(300)) shouldBe 306
    }
    "calculate for pool = 1700, accounting period for a leap year" in new WritingDownAllowanceCalculator {
      writtenDownAllowanceLimit(cp1 = CP1(LocalDate.parse("2012-01-01")),
                                cp2 = CP2(LocalDate.parse("2012-12-31")),
                                cp78 = CP78(1000),
                                cp82 = CP82(1000),
                                cp84 = CP84(0),
                                cp88 = CP88(300)) shouldBe 306
    }
  }
}
