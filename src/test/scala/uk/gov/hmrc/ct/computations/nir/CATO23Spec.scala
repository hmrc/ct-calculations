/*
 * Copyright 2018 HM Revenue & Customs
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

package uk.gov.hmrc.ct.computations.nir

import org.mockito.Mockito.when
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mock.MockitoSugar
import uk.gov.hmrc.ct.computations.CP997NI
import uk.gov.hmrc.ct.{BoxValidationFixture, CATO01, CATO23}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CATO23Spec extends WordSpec with Matchers with MockitoSugar with BoxValidationFixture[ComputationsBoxRetriever] {

  override val boxRetriever = makeBoxRetriever()

  private def makeBoxRetriever(cato01Value: Int = 500, cp997NIValue: Option[Int] = Some(200)) = {

    val retriever = mock[ComputationsBoxRetriever]
    when(retriever.cato01()).thenReturn(CATO01(cato01Value))
    when(retriever.cp997NI()).thenReturn(CP997NI(cp997NIValue))
    retriever
  }

  "CATO23" should{
    "be calculated" in{
      CATO23.calculate(makeBoxRetriever()) shouldBe  CATO23(300)
    }
  }

}
