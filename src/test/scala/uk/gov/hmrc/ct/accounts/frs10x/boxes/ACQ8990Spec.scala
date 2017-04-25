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

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDormancyBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class ACQ8990Spec extends WordSpec with Matchers with MockitoSugar with BeforeAndAfterEach {

  val mockBoxRetriever = mock[Frs10xDormancyBoxRetriever]

  "ACQ8990 should" should {

    "validate successfully when not set and profitAndLossStatementRequired false" in {
      when(mockBoxRetriever.profitAndLossStatementRequired()).thenReturn(ProfitAndLossStatementRequired(false))
      ACQ8990(None).validate(mockBoxRetriever) shouldBe Set.empty
    }

    "validate successfully when set and profitAndLossStatementRequired true" in {
      when(mockBoxRetriever.profitAndLossStatementRequired()).thenReturn(ProfitAndLossStatementRequired(true))
      ACQ8990(Some(true)).validate(mockBoxRetriever) shouldBe Set.empty
      ACQ8990(Some(false)).validate(mockBoxRetriever) shouldBe Set.empty
    }

    "fail validation if not set and nd profitAndLossStatementRequired true" in {
      when(mockBoxRetriever.profitAndLossStatementRequired()).thenReturn(ProfitAndLossStatementRequired(true))
      ACQ8990(None).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("ACQ8990"), "error.ACQ8990.required", None))
    }
  }
}
