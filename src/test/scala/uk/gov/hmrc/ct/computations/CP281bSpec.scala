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

import org.joda.time.LocalDate
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import losses.lossReform2017
import org.mockito.Mockito.when

class CP281bSpec extends WordSpec with Matchers with MockitoSugar with BoxValidationFixture[ComputationsBoxRetriever] {

  override val boxRetriever = makeBoxRetriever()

  testMandatoryWhen("CP281b", CP281b.apply) {
    makeBoxRetriever()
  }

  testBoxIsZeroOrPositive("CP281b", CP281b.apply)

  testCannotExistWhen("CP281b", CP281b.apply, testDetails = "CPQ17 false") {
    makeBoxRetriever(cpq17Value = false)
  }

  testCannotExistWhen("CP281b", CP281b.apply, testDetails = "before loss reform") {
    makeBoxRetriever(cp2Value = lossReform2017)
  }

  "CP281b" should {
    "fail if the sum of CP283b, CP288b and CP997 is not equal to CP281b or 0" in {
      CP281b(4).validate(makeBoxRetriever(cp281aValue = Some(2),cp283bValue = Some(1), cp288bValue = Some(2), cp997Value = Some(3))).contains(CtValidation(None, "error.CP281b.breakdown.sum.incorrect")) shouldBe true
    }
    "pass if the sum of CP283b, CP288b and CP997 is equal to CP281b" in {
      CP281b(3).validate(makeBoxRetriever(cp281aValue = Some(0),cp283bValue = Some(1), cp288bValue = Some(0), cp997Value = Some(2))).contains(CtValidation(None, "error.CP281b.breakdown.sum.incorrect")) shouldBe false
    }
  }

  private def makeBoxRetriever(cp281aValue: Option[Int] = Some(1), cp281cValue: Option[Int] = Some(1), cp281dValue: Option[Int] = Some(1), cp2Value: LocalDate = lossReform2017.plusDays(1), cpq17Value: Boolean = true,
                               cp283bValue: Option[Int] = Some(1), cp288bValue: Option[Int] = Some(1), cp997Value: Option[Int] = Some(1)) = {
    val retriever = mock[ComputationsBoxRetriever]
    when(retriever.cp281a()).thenReturn(CP281a(cp281aValue))
    when(retriever.cp281c()).thenReturn(CP281c(cp281cValue))
    when(retriever.cp281d()).thenReturn(CP281d(cp281dValue))
    when(retriever.cp2()).thenReturn(CP2(cp2Value))
    when(retriever.cpQ17()).thenReturn(CPQ17(Some(cpq17Value)))
    when(retriever.cp283b()).thenReturn(CP283b(cp283bValue))
    when(retriever.cp288b()).thenReturn(CP288b(cp288bValue))
    when(retriever.chooseCp997()).thenReturn(CP997(cp997Value))
    retriever
  }

}