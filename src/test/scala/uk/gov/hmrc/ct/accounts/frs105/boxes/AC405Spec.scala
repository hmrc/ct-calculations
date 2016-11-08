/*
 * Copyright 2016 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs105.boxes

import org.mockito.Mockito._
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AccountsMoneyValidationFixture, MockFrs105AccountsRetriever}
import uk.gov.hmrc.ct.box.CtValidation

class AC405Spec extends AccountsMoneyValidationFixture[Frs105AccountsBoxRetriever] with MockFrs105AccountsRetriever {

  def setupCurrentYearMocks(ac405: AC405, ac410: AC410, ac415: AC415, ac420: AC420, ac425: AC425, ac34: AC34) = {
    when(boxRetriever.ac405()).thenReturn(ac405)
    when(boxRetriever.ac410()).thenReturn(ac410)
    when(boxRetriever.ac415()).thenReturn(ac415)
    when(boxRetriever.ac420()).thenReturn(ac420)
    when(boxRetriever.ac425()).thenReturn(ac425)
    when(boxRetriever.ac34()).thenReturn(ac34)
  }

  override def setUpMocks(): Unit = {
    setupCurrentYearMocks(AC405(None), AC410(Some(1)), AC415(None), AC420(None), AC425(None), AC34(None))
    super.setUpMocks()
  }

  testAccountsMoneyValidation("AC405", AC405.apply)

  "AC405 validation" should {
    "fail if at least one current year box not populated" in {
      setupCurrentYearMocks(AC405(None), AC410(None), AC415(None), AC420(None), AC425(None), AC34(None))
      AC405(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.profit.loss.one.box.required", None))
    }
  }

  "AC405 validation" should {
    "pass if at least one current year box populated" in {
      setupCurrentYearMocks(AC405(Some(1)), AC410(None), AC415(None), AC420(None), AC425(None), AC34(None))
      AC405(None).validate(boxRetriever) shouldBe Set.empty

      setupCurrentYearMocks(AC405(None), AC410(Some(1)), AC415(None), AC420(None), AC425(None), AC34(None))
      AC405(None).validate(boxRetriever) shouldBe Set.empty

      setupCurrentYearMocks(AC405(None), AC410(None), AC415(Some(1)), AC420(None), AC425(None), AC34(None))
      AC405(None).validate(boxRetriever) shouldBe Set.empty

      setupCurrentYearMocks(AC405(None), AC410(None), AC415(None), AC420(Some(1)), AC425(None), AC34(None))
      AC405(None).validate(boxRetriever) shouldBe Set.empty

      setupCurrentYearMocks(AC405(None), AC410(None), AC415(None), AC420(None), AC425(Some(1)), AC34(None))
      AC405(None).validate(boxRetriever) shouldBe Set.empty

      setupCurrentYearMocks(AC405(None), AC410(None), AC415(None), AC420(None), AC425(None), AC34(Some(1)))
      AC405(None).validate(boxRetriever) shouldBe Set.empty
    }
  }
}