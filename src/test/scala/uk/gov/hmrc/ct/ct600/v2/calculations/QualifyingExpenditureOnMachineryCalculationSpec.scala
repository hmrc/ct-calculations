/*
 * Copyright 2015 HM Revenue & Customs
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

package uk.gov.hmrc.ct.ct600.v2.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.calculations.QualifyingExpenditureOnMachineryCalculation
import uk.gov.hmrc.ct.computations.{CP253, CP81, CP82, CP87}

class QualifyingExpenditureOnMachineryCalculationSpec extends WordSpec with Matchers {

  "QualifyingExpenditureOnMachinery" should {

    "return 0 if CP82 is None" in new QualifyingExpenditureOnMachineryCalculation {
      qualifyingExpenditureCalculation(CP82(None), CP87(123), CP81(456)) shouldBe CP253(0)
    }

    "return 0 if CP82 is zero" in new QualifyingExpenditureOnMachineryCalculation {
      qualifyingExpenditureCalculation(CP82(Some(0)), CP87(123), CP81(456)) shouldBe CP253(0)
    }

    "return the calculated value if CP82 > 0" in new QualifyingExpenditureOnMachineryCalculation {
      qualifyingExpenditureCalculation(CP82(Some(100)), CP87(25), CP81(50)) shouldBe CP253(125)
    }
  }
}
