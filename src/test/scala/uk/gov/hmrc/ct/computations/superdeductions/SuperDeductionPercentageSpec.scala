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

package uk.gov.hmrc.ct.computations.superdeductions

import java.time.LocalDate
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import uk.gov.hmrc.ct.accounts.{AC3, AC4, AC5, AC6}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod

class SuperDeductionPercentageSpec extends AnyWordSpec with Matchers {
  val superDeductionPeriod = SuperDeductionPeriod(AC5(LocalDate.of(2021,4,1)), AC6(LocalDate.of(2023,3,31)))
  "SuperDeductionPercentage" should {
    "be 0 if filing period does not overlap super deduction period " in {
      val hmrcAccountingPeriod = HmrcAccountingPeriod(AC3(LocalDate.of(2019,4,1)), AC4(LocalDate.of(2020,3,31)))
      SuperDeductionPercentage(hmrcAccountingPeriod, superDeductionPeriod).percentage should be(0)
    }

    "be 1.30 if filing period is between  super deduction period " in {
      val hmrcAccountingPeriod = HmrcAccountingPeriod(AC3(LocalDate.of(2021,5,1)), AC4(LocalDate.of(2022,4,30)))
      SuperDeductionPercentage(hmrcAccountingPeriod, superDeductionPeriod).percentage should be(BigDecimal(1.30000))
    }

    "be 1.14959 if filing period overlaps  super deduction period by 182 days" in {
      val hmrcAccountingPeriod = HmrcAccountingPeriod(AC3(LocalDate.of(2022,10,1)), AC4(LocalDate.of(2023,9,30)))
      SuperDeductionPercentage(hmrcAccountingPeriod, superDeductionPeriod).percentage should be(BigDecimal(1.14959))
    }

    "be 1.07397 if filing period overlaps  super deduction period by 90 days" in {
      val hmrcAccountingPeriod = HmrcAccountingPeriod(AC3(LocalDate.of(2023,1,1)), AC4(LocalDate.of(2023,12,31)))
      SuperDeductionPercentage(hmrcAccountingPeriod, superDeductionPeriod).percentage should be(1.07397)
    }


    "be 1.04849 if filing period overlaps  super deduction period by 59 days" in {
      val hmrcAccountingPeriod = HmrcAccountingPeriod(AC3(LocalDate.of(2023,2,1)), AC4(LocalDate.of(2024,1,31)))
      SuperDeductionPercentage(hmrcAccountingPeriod, superDeductionPeriod).percentage should be(1.04849)
    }


    "be correct for leap year" in {
      val hmrcAccountingPeriod = HmrcAccountingPeriod(AC3(LocalDate.of(2023,3,10)), AC4(LocalDate.of(2024,3,9)))
      SuperDeductionPercentage(hmrcAccountingPeriod, superDeductionPeriod).percentage should be(1.01803)
    }

    "be 1.3 if end date of HMRC accounting period overlaps even by a day" in {
      val hmrcAccountingPeriod = HmrcAccountingPeriod(AC3(LocalDate.of(2020,4,2)), AC4(LocalDate.of(2021,4,1)))
      SuperDeductionPercentage(hmrcAccountingPeriod, superDeductionPeriod).percentage should be(1.30000)
    }

    "be 1.3 if end date of HMRC accounting period overlaps even by a few day" in {
      val hmrcAccountingPeriod = HmrcAccountingPeriod(AC3(LocalDate.of(2020,5,2)), AC4(LocalDate.of(2021,5,1)))
      SuperDeductionPercentage(hmrcAccountingPeriod, superDeductionPeriod).percentage should be(1.30000)
    }
  }
}
