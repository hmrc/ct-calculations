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
import uk.gov.hmrc.ct.computations.{CP501, CP502}

class NonBankInterestSimilarIncomeReceivableCalculatorSpec extends WordSpec with Matchers {

  "NonBankInterestSimilarIncomeReceivableCalculator" should {
    "sum up the gross interest from property and the ancilliary income if both are defined" in new NonBankInterestSimilarIncomeReceivableCalculator {
      val grossInterest = CP501(Some(100))
      val ancilliaryIncome = CP502(Some(200))
      nonBankInterestSimilarIncomeReceivableCalculation(grossInterest, ancilliaryIncome) should be (Some(300))
    }
    "return None if the gross interest from property is not defined" in new NonBankInterestSimilarIncomeReceivableCalculator {
      val grossInterest = CP501(None)
      val ancilliaryIncome = CP502(Some(200))
      nonBankInterestSimilarIncomeReceivableCalculation(grossInterest, ancilliaryIncome) should be (None)
    }
    "return None if the ancilliary income is not defined" in new NonBankInterestSimilarIncomeReceivableCalculator {
      val grossInterest = CP501(Some(200))
      val ancilliaryIncome = CP502(None)
      nonBankInterestSimilarIncomeReceivableCalculation(grossInterest, ancilliaryIncome) should be (None)
    }
    "return None if both gross interest from property and ancilliary income are not defined" in new NonBankInterestSimilarIncomeReceivableCalculator{
      val grossInterest = CP501(None)
      val ancilliaryIncome = CP502(None)
      nonBankInterestSimilarIncomeReceivableCalculation(grossInterest, ancilliaryIncome) should be (None)
    }
  }

}
