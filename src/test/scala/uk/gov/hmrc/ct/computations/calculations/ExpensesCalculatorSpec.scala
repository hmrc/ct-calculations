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

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations._
import uk.gov.hmrc.ct.{CATO14, CATO15, CATO16}

class ExpensesCalculatorSpec extends WordSpec with Matchers {

  "Calculating total expenses - CP38" should {
    "calculate as CATO14(Directors Expenses) + CATO15(Property Expenses) + CATO15(General Administrative Expenses)" in new ExpensesCalculator {
      val cato14 = CATO14(95)
      val cato15 = CATO15(96)
      val cato16 = CATO16(97)
      val result = calculateTotalExpenses(cato14, cato15, cato16)
      val expectedResult = cato14 + cato15 + cato16
      result shouldBe CP38(expectedResult)
    }
  }
  
  "Calculating directors expenses - CATO14" should {
    "calculate as CP15 + CP16 + CP17 + CP18 + CP19 + CP20 + CP21" in new ExpensesCalculator {
      val cp15 = CP15(Some(1))
      val cp16 = CP16(Some(2))
      val cp17 = CP17(Some(3))
      val cp18 = CP18(Some(4))
      val cp19 = CP19(Some(5))
      val cp20 = CP20(Some(6))
      val cp21 = CP21(Some(7))
      val result = calculateDirectorsExpenses(cp15, cp16, cp17, cp18, cp19, cp20, cp21)
      val expectedResult = cp15 + cp16 + cp17 + cp18 + cp19 + cp20 + cp21
      result shouldBe CATO14(expectedResult)
    }
  }
  
  "Calculating property expenses - CATO15" should {
    "calculate as CP22 + CP23 + CP24" in new ExpensesCalculator {
      val cp22 = CP22(Some(8))
      val cp23 = CP23(Some(9))
      val cp24 = CP24(Some(0))
      val result = calculatePropertyExpenses(cp22, cp23, cp24)
      val expectedResult = cp22 + cp23 + cp24
      result shouldBe CATO15(expectedResult)
    }
  }
  
  "Calculating General Administrative Expenses - CATO16" should {
    "calculate as CP25 + CP26 + CP27 + CP28 + CP29 + CP30 + CP31 + CP32 + CP33 + CP34 + CP35 + CP36 + CP37" in new ExpensesCalculator {
      val cp25 = CP25(Some(11))
      val cp26 = CP26(Some(12))
      val cp27 = CP27(Some(13))
      val cp28 = CP28(Some(14))
      val cp29 = CP29(Some(15))
      val cp30 = CP30(Some(16))
      val cp31 = CP31(Some(17))
      val cp32 = CP32(Some(18))
      val cp33 = CP33(Some(19))
      val cp34 = CP34(Some(20))
      val cp35 = CP35(Some(21))
      val cp36 = CP36(Some(22))
      val cp37 = CP37(Some(23))
      val result = calculateGeneralAdministrativeExpenses(cp25, cp26, cp27, cp28, cp29, cp30, cp31, cp32, cp33, cp34, cp35, cp36, cp37)
      val expectedResult = cp25 + cp26 + cp27 + cp28 + cp29 + cp30 + cp31 + cp32 + cp33 + cp34 + cp35 + cp36 + cp37
      result shouldBe CATO16(expectedResult)
    }
  }
  
}
