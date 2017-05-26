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

import org.joda.time.LocalDate
import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

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
    makeBoxRetriever(cp1Value = CPQ17.lossReform2017)
  }

  "CP281a" should {
    "fail if the sum of CP283b, CP288b and CP997 is less than CP281a" in {
      CP281b(4).validate(makeBoxRetriever()).contains(CtValidation(None, "error.CP281b.breakdown.sum.incorrect")) shouldBe true
    }
    "fail if the sum of CP283b, CP288b and CP997 is greater than CP281a" in {
      CP281b(2).validate(makeBoxRetriever()).contains(CtValidation(None, "error.CP281b.breakdown.sum.incorrect")) shouldBe true
    }
    "pass if the sum of CP283b, CP288b and CP997 is equal to CP281a" in {
      CP281b(3).validate(makeBoxRetriever()).contains(CtValidation(None, "error.CP281b.breakdown.sum.incorrect")) shouldBe false
    }
  }

  private def makeBoxRetriever(cp1Value: LocalDate = CPQ17.lossReform2017.plusDays(1), cpq17Value: Boolean = true,
                              cp283bValue: Option[Int] = Some(1), cp288bValue: Option[Int] = Some(1), cp997Value: Option[Int] = Some(1)) = {
    val retriever = mock[ComputationsBoxRetriever]
    when(retriever.cp1()).thenReturn(CP1(cp1Value))
    when(retriever.cpQ17()).thenReturn(CPQ17(Some(cpq17Value)))
    when(retriever.cp283b()).thenReturn(CP283b(cp283bValue))
    when(retriever.cp288b()).thenReturn(CP288b(cp288bValue))
    when(retriever.cp997()).thenReturn(CP997(cp997Value))
    retriever
  }
}
