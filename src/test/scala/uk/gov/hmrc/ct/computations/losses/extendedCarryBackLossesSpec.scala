/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.ct.computations.losses

import java.time.LocalDate
import uk.gov.hmrc.ct.computations.losses
import uk.gov.hmrc.ct.utils.UnitSpec

class extendedCarryBackLossesSpec extends UnitSpec {
  "The Extended Carry Back Loss Period" should {
    "be false for end date before 01 April 2020" in {
      val endDate = LocalDate.parse("2020-03-31")

      val result = losses.doesPeriodCoverECBL(endDate)
      result shouldBe false
    }

    "be true for end date on 01 April 2020" in {
      val endDate = LocalDate.parse("2020-04-01")

      val result = losses.doesPeriodCoverECBL(endDate)
      result shouldBe true
    }

    "be true for period with end date landing between 01 April 2020 and 31 March 2022" in {
      val endDate = LocalDate.parse("2021-12-31")

      val result = losses.doesPeriodCoverECBL(endDate)
      result shouldBe true
    }

    "be false for end date after 31 March 2022" in {
      val endDate = LocalDate.parse("2022-04-01")

      val result = losses.doesPeriodCoverECBL(endDate)
      result shouldBe false
    }
  }
}
