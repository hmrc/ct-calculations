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

package uk.gov.hmrc.ct.ct600e.v3.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.ct600e.v3._

class ExpenditureCalculatorSpec extends WordSpec with ExpenditureCalculator with Matchers {

  "ExpenditureCalculator" should {
    "return None if all Expenditure boxes are None" in {
      calculateTotalExpenditure(e95 = E95(None), e100 = E100(None), e105 = E105(None), e110 = E110(None), e115 = E115(None), e120 = E120(None)) shouldBe E125(None)
    }

    "return sum of populated boxes if some Expenditure boxes have values" in {
      calculateTotalExpenditure(e95 = E95(Some(95)), e100 = E100(Some(100)), e105 = E105(Some(105)), e110 = E110(None), e115 = E115(None), e120 = E120(None)) shouldBe E125(Some(300))
    }

    "return sum of all boxes if all Expenditure boxes have values" in {
      calculateTotalExpenditure(e95 = E95(Some(95)), e100 = E100(Some(100)), e105 = E105(Some(105)), e110 = E110(Some(110)), e115 = E115(Some(115)), e120 = E120(Some(120))) shouldBe E125(Some(645))
    }

    "return sum of all boxes if some Expenditure boxes have the same value" in {
      calculateTotalExpenditure(e95 = E95(Some(95)), e100 = E100(Some(100)), e105 = E105(Some(95)), e110 = E110(Some(110)), e115 = E115(Some(115)), e120 = E120(Some(120))) shouldBe E125(Some(635))
    }
  }
}
