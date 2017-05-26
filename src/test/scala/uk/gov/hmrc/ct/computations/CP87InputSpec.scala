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

package uk.gov.hmrc.ct.computations

import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever


class CP87InputSpec extends WordSpec with MockitoSugar with Matchers with BoxValidationFixture[ComputationsBoxRetriever] with BeforeAndAfterEach {

  val boxRetriever = mock[ComputationsBoxRetriever]

  override def setUpMocks = {
    when(boxRetriever.cpQ8()).thenReturn(CPQ8(Some(false)))
    when(boxRetriever.cp81()).thenReturn(CP81(5555))
    when(boxRetriever.cpAux1()).thenReturn(CPAux1(5555))
  }

  override def beforeEach = setUpMocks

  testBoxIsZeroOrPositive("CP87Input", CP87Input.apply)

  testCannotExistWhen("CP87Input", CP87Input.apply) {
    when(boxRetriever.cp81()).thenReturn(CP81(5555))
    when(boxRetriever.cpAux1()).thenReturn(CPAux1(5555))
    when(boxRetriever.cpQ8()).thenReturn(CPQ8(Some(true))).getMock[ComputationsBoxRetriever]
  }

  "fail validation if is bigger than max FYA" in {
    when(boxRetriever.cp81()).thenReturn(CP81(10))
    when(boxRetriever.cpAux1()).thenReturn(CPAux1(10))

    CP87Input(Some(21)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP87Input"), "error.CP87Input.firstYearAllowanceClaimExceedsAllowance", Some(List("20"))))
  }

  "fail validation if cpQ8 is false and box is empty" in {
    CP87Input(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP87Input"), "error.CP87Input.fieldMustHaveValueIfTrading", None))
  }
}
