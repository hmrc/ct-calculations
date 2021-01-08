/*
 * Copyright 2021 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts._
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs105.stubs.StubbedFrs105AccountsBoxRetriever

class AC436Spec extends AccountsMoneyValidationFixture[Frs105AccountsBoxRetriever] with MockFrs105AccountsRetriever {

  "AC435 calculates from boxes correctly" in {
    val testAc13 = 123
    val testAc406 = 234
    val testAc411 = 345
    val testAc416 = 456
    val testAc421 = 567
    val testAc426 = 678
    val testAc35 = 789
    val testAc402 = 890
    val testAc404 = 901
    val boxRetriever = new StubbedFrs105AccountsBoxRetriever {
      override def ac13 = AC13(Some(testAc13))
      override def ac406 = AC406(Some(testAc406))
      override def ac411 = AC411(Some(testAc411))
      override def ac416 = AC416(Some(testAc416))
      override def ac421 = AC421(Some(testAc421))
      override def ac426 = AC426(Some(testAc426))
      override def ac35 = AC35(Some(testAc35))
      override def ac402 = AC402(testAc402)
      override def ac404 = AC404(Some(testAc404))
    }

    val ac436 = AC436.calculate(boxRetriever)

    ac436.value shouldBe Some(
        testAc13 +
        testAc406 +
        testAc402 -
        testAc411 -
        testAc416 -
        testAc421 -
        testAc426 -
        testAc35 -
        testAc404
    )
  }
}
