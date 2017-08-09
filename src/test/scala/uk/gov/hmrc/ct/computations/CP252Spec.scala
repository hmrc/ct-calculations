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
import org.scalatest.{BeforeAndAfter, BeforeAndAfterEach, Matchers, WordSpec}
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever


class CP252Spec extends WordSpec with MockitoSugar with Matchers with BoxValidationFixture[ComputationsBoxRetriever] with BeforeAndAfterEach {

  val boxRetriever = mock[ComputationsBoxRetriever]

  override def setUpMocks = {
    when(boxRetriever.cpQ8()).thenReturn(CPQ8(Some(false)))
    when(boxRetriever.cp79()).thenReturn(CP79(Some(333)))
  }

  override def beforeEach = setUpMocks

  testBoxIsZeroOrPositive("CP252", CP252.apply)

  testCannotExistWhen("CP252", CP252.apply) {
    when(boxRetriever.cpQ8()).thenReturn(CPQ8(Some(true)))
    when(boxRetriever.cp79()).thenReturn(CP79(Some(333))).getMock[ComputationsBoxRetriever]
  }

  "fail validation when higher than cp79" in {
    CP252(Some(444)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP252"), "error.CP252.exceedsRelevantFYAExpenditure", None))
  }
}
