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

import org.scalatest.prop.TableDrivenPropertyChecks.forAll
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations._
import org.scalatest.prop.Tables.Table

class LossesCarriedForwardsCalculatorSpec extends WordSpec with Matchers {

  val table = Table(
    ("CP281", "CP118", "CP283", "CP998", "CP997", "CP287", "result"),
    (      0,       0,       0,       0,       0,       0,        0),
    (    100,       0,      80,       0,       0,       0,       20),
    (    100,       0,      80,       0,      10,       0,       10),
    (      0,     100,       0,       0,       0,      80,       20),
    (      0,     100,       0,      30,       0,       0,       70),
    (      0,     100,       0,      30,       0,      10,       60),
    (     50,     100,       0,     100,      20,      10,       20)
  )

  "Losses carried forward" in new LossesCarriedForwardsCalculator {
    forAll(table) {
      (cp281: Int,
       cp118: Int,
       cp283: Int,
       cp998: Int,
       cp997: Int,
       cp287: Int,
       result: Int) => {
        lossesCarriedForwardsCalculation(
          CP281(cp281), CP118(cp118), CP283(Some(cp283)),
          CP998(Some(cp998)), CP287(cp287), CP997(cp997)
        ) shouldBe CP288(Some(result))
      }
    }
  }
}
