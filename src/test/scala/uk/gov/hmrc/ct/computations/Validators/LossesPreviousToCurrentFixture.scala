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

package uk.gov.hmrc.ct.computations.Validators

import org.mockito.Mockito._
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mock.MockitoSugar
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}
import uk.gov.hmrc.ct.computations.{CP117, CP283a, CP283b}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

trait LossesPreviousToCurrentFixture extends WordSpec with Matchers with MockitoSugar {

  def testGlobalErrorsForBroughtForwardGtTotalProfit(box: ComputationsBoxRetriever => ValidatableBox[ComputationsBoxRetriever])(boxRetriever: ComputationsBoxRetriever) = {

    "CP283a + CP283b" should {
      "be allowed to equal total profit" in {
        when(boxRetriever.cp117()).thenReturn(CP117(100))
        when(boxRetriever.cp283a()).thenReturn(CP283a(50))
        when(boxRetriever.cp283b()).thenReturn(CP283b(50))
        box(boxRetriever).validate(boxRetriever).contains(CtValidation(None, "error.CP283.exceeds.totalProfit")) shouldBe false
      }
      "be allowed to be less than total profit" in {
        when(boxRetriever.cp117()).thenReturn(CP117(100))
        when(boxRetriever.cp283a()).thenReturn(CP283a(49))
        when(boxRetriever.cp283b()).thenReturn(CP283b(50))
        box(boxRetriever).validate(boxRetriever).contains(CtValidation(None, "error.CP283.exceeds.totalProfit")) shouldBe false
      }
      "no be allowed to be greater than total profit" in {
        when(boxRetriever.cp117()).thenReturn(CP117(100))
        when(boxRetriever.cp283a()).thenReturn(CP283a(51))
        when(boxRetriever.cp283b()).thenReturn(CP283b(50))
        box(boxRetriever).validate(boxRetriever).contains(CtValidation(None, "error.CP283.exceeds.totalProfit")) shouldBe true
      }
    }
  }
}
