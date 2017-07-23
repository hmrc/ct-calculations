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
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP3010Spec extends WordSpec with Matchers with MockitoSugar with BoxValidationFixture[ComputationsBoxRetriever] {

  override val boxRetriever = makeBoxRetriever(true)

  testMandatoryWhen("CP3010", CP3010.apply, validValue = 1)

  testBoxIsZeroOrPositive("CP3010", CP3010.apply)

  testBecauseOfDependendBoxThenCannotExist("CP3010", CP3010.apply) {
    makeBoxRetriever(false)
  }

  private def makeBoxRetriever(cpq321Value: Boolean) = {
    val retriever = mock[ComputationsBoxRetriever]
    when(retriever.cpQ321()).thenReturn(CPQ321(Some(cpq321Value)))
    retriever
  }
}
