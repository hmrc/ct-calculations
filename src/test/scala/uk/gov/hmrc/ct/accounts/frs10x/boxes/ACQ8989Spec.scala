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
import org.scalatest.BeforeAndAfterEach
import uk.gov.hmrc.ct.accounts.AccountStatementValidationFixture
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDormancyBoxRetriever

class ACQ8989Spec extends AccountStatementValidationFixture[Frs10xDormancyBoxRetriever] with BeforeAndAfterEach {

  override val boxRetriever = mock[Frs10xDormancyBoxRetriever] (RETURNS_SMART_NULLS)

  override def setupMocks() = {
    when(boxRetriever.notTradedStatementRequired()).thenReturn(NotTradedStatementRequired(true))
  }

  doStatementValidationTests("ACQ8989", ACQ8989.apply)

  "ACQ8989 should" should {

    "validate successfully when not set and notTradedStatementRequired false" in {
      when(boxRetriever.notTradedStatementRequired()).thenReturn(NotTradedStatementRequired(false))
      ACQ8989(None).validate(boxRetriever) shouldBe Set.empty
    }
  }
}
