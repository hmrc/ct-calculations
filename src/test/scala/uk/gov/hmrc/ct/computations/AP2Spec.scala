/*
 * Copyright 2020 HM Revenue & Customs
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

package uk.gov.hmrc.ct.computations

import org.mockito.Mockito.when
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AC12, AC401, AC403}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class AP2Spec extends WordSpec with Matchers with BoxValidationFixture[AccountsBoxRetriever with ComputationsBoxRetriever] {

  trait AccountsBoxRetrieverWithComputationsBoxRetriever extends AccountsBoxRetriever with ComputationsBoxRetriever with FilingAttributesBoxValueRetriever
  override val boxRetriever = mock[AccountsBoxRetrieverWithComputationsBoxRetriever]

  testBoxIsZeroOrPositive("AP2", v => AP2(v))

  override def setUpMocks(): Unit = {
    when(boxRetriever.ap1()).thenReturn(AP1(Some(1)))
    when(boxRetriever.ap2()).thenReturn(AP2(Some(1)))
    when(boxRetriever.ap3()).thenReturn(AP3(Some(1)))
    when(boxRetriever.ac12()).thenReturn(AC12(Some(3)))
    when(boxRetriever.ac401()).thenReturn(AC401(Some(4)))
    when(boxRetriever.ac403()).thenReturn(AC403(Some(2)))
  }

  "AP2" should {
    "not allow the total of it, AP1 and AP3 to be greater than AC12" in {
      when(boxRetriever.ac12()).thenReturn(AC12(Some(2)))
      AP2(Some(1)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.apportionmentTurnover.total"))
    }
    "not allow the total of it, AP1 and AP3 to be less than AC12" in {
      when(boxRetriever.ac12()).thenReturn(AC12(Some(4)))
      AP2(Some(1)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.apportionmentTurnover.total"))
    }
    "allow the total of it, AP1 and AP3 to be equal to AC12 + AC401 - AC403" in {
      when(boxRetriever.ac12()).thenReturn(AC12(Some(3)))
      AP2(Some(3)).validate(boxRetriever) shouldBe empty
    }
  }
}
