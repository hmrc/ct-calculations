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
import uk.gov.hmrc.ct.accounts.AccountStatementValidationFixture
import uk.gov.hmrc.ct.accounts.frs102.helper.DirectorsReportEnabled
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

class AC8899Spec extends AccountStatementValidationFixture[Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever] {

  trait MockRetriever extends Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever
  override val boxRetriever: MockRetriever = mock[MockRetriever] (RETURNS_SMART_NULLS)

  override def setupMocks() = {
    when(boxRetriever.directorsReportEnabled()).thenReturn(DirectorsReportEnabled(true))
  }

  doStatementValidationTests("AC8899", AC8899.apply)

  "validation passes if not enabled" in {
    when(boxRetriever.directorsReportEnabled()).thenReturn(DirectorsReportEnabled(false))
    AC8899(None).validate(boxRetriever) shouldBe Set.empty
  }
}
