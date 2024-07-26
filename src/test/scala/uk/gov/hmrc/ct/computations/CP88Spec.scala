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

package uk.gov.hmrc.ct.computations

import org.mockito.Mockito.when
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import uk.gov.hmrc.ct.{BoxValidationFixture, CATO02}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever


class CP88Spec extends AnyWordSpec with MockitoSugar with Matchers with BoxValidationFixture[ComputationsBoxRetriever] with BeforeAndAfterEach {

  val boxRetriever = mock[ComputationsBoxRetriever]

  override def setUpMocks = {
    when(boxRetriever.cpQ8()).thenReturn(CPQ8(Some(false)))
    when(boxRetriever.cp83()).thenReturn(CP83(5555))
    when(boxRetriever.cato02()).thenReturn(CATO02(5555))
  }

  override def beforeEach = setUpMocks()

  testBoxIsZeroOrPositive("CP88", CP88.apply)

  testCannotExistWhen("CP88", CP88.apply) {
    when(boxRetriever.cp83()).thenReturn(CP83(5555))
    when(boxRetriever.cato02()).thenReturn(CATO02(5555))
    when(boxRetriever.cpQ8()).thenReturn(CPQ8(Some(true))).getMock[ComputationsBoxRetriever]
  }

  "fail validation if is bigger than max AIA" in {
    when(boxRetriever.cp83()).thenReturn(CP83(10))
    when(boxRetriever.cato02()).thenReturn(CATO02(10))

    CP88(Some(21)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP88"), "error.CP88.annualInvestmentAllowanceExceeded", Some(List("10"))))
  }

  "fail validation if cpQ8 is false and box is empty" in {
    CP88(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP88"), "error.CP88.fieldMustHaveValueIfTrading", None))
  }
}
