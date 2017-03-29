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
import uk.gov.hmrc.ct.accounts.frs10x.retriever.{Frs10xAccountsBoxRetriever, Frs10xDirectorsBoxRetriever, Frs10xDormancyBoxRetriever, Frs10xFilingQuestionsBoxRetriever}
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever


trait MockRetriever extends Frs10xAccountsBoxRetriever with Frs10xDormancyBoxRetriever {
  self: FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever with Frs10xDirectorsBoxRetriever =>
}

class AC8081Spec extends AccountStatementValidationFixture[Frs10xAccountsBoxRetriever with Frs10xDormancyBoxRetriever] with BeforeAndAfterEach {

  override val boxRetriever = mock[MockRetriever] (RETURNS_SMART_NULLS)

  override def setupMocks = {
    when(boxRetriever.acq8999()).thenReturn(ACQ8999(None))
  }

  doStatementValidationTests("AC8081", AC8081.apply)


  "validation disabled if dormant" in {
    when(boxRetriever.acq8999()).thenReturn(ACQ8999(Some(true)))

    AC8081(None).validate(boxRetriever) shouldBe Set.empty
    AC8081(Some(true)).validate(boxRetriever) shouldBe Set.empty
  }
}
