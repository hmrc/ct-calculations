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
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.computations.Validators.LossesPreviousToCurrentFixture
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP283bSpec extends WordSpec with Matchers with MockitoSugar with BoxValidationFixture[ComputationsBoxRetriever] with LossesPreviousToCurrentFixture {

  override val boxRetriever = makeBoxRetriever()

  testMandatoryWhen("CP283b", CP283b.apply) {
    makeBoxRetriever()
  }

  testBoxIsZeroOrPositive("CP283b", CP283b.apply)

  testGlobalErrorsForBroughtForwardGtTotalProfit(b => b.cp283b()) {
    makeBoxRetriever()
  }

  private def makeBoxRetriever(cp281bValue: Option[Int] = Some(1)) = {
    val retriever = mock[ComputationsBoxRetriever]
    when(retriever.cp281b()).thenReturn(CP281b(cp281bValue))
    when(retriever.cp283a()).thenReturn(CP283a(None))
    when(retriever.cp283b()).thenReturn(CP283b(None))
    when(retriever.cp117()).thenReturn(CP117(0))
    retriever
  }
}
