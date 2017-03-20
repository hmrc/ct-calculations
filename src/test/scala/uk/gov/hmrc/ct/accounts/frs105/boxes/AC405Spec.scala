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

package uk.gov.hmrc.ct.accounts.frs105.boxes

import org.mockito.Mockito._
import uk.gov.hmrc.ct.accounts.frs10x.boxes.{ACQ8161, ACQ8999}
import uk.gov.hmrc.ct.{CompaniesHouseFiling, HMRCFiling}
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs10x.retriever.{Frs10xDormancyBoxRetriever, Frs10xFilingQuestionsBoxRetriever}
import uk.gov.hmrc.ct.accounts.{AC12, AccountsMoneyValidationFixture, MockFrs105AccountsRetriever}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

class AC405Spec extends AccountsMoneyValidationFixture[Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever with Frs10xDormancyBoxRetriever] with  MockFrs105AccountsRetriever {

  def setupCurrentYearMocks(ac12: AC12, ac405: AC405, ac410: AC410, ac415: AC415, ac420: AC420, ac425: AC425, ac34: AC34) = {
    when(boxRetriever.ac12()).thenReturn(ac12)
    when(boxRetriever.ac405()).thenReturn(ac405)
    when(boxRetriever.ac410()).thenReturn(ac410)
    when(boxRetriever.ac415()).thenReturn(ac415)
    when(boxRetriever.ac420()).thenReturn(ac420)
    when(boxRetriever.ac425()).thenReturn(ac425)
    when(boxRetriever.ac34()).thenReturn(ac34)
  }

  override def setUpMocks(): Unit = {
    setupCurrentYearMocks(AC12(None), AC405(None), AC410(Some(1)), AC415(None), AC420(None), AC425(None), AC34(None))
    when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
    when(boxRetriever.acq8999()).thenReturn(ACQ8999(None))
    super.setUpMocks()
  }

  testAccountsMoneyValidation("AC405", AC405.apply)

  "AC405 validation" should {
    "fail if no current year box populated" in {
      when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      setupCurrentYearMocks(AC12(None), AC405(None), AC410(None), AC415(None), AC420(None), AC425(None), AC34(None))

      AC405(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.profit.loss.one.box.required", None))
    }
  }

  "AC405 validation" should {
    "pass if at least one current year box populated" in {
      when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))

      setupCurrentYearMocks(AC12(Some(1)), AC405(None), AC410(None), AC415(None), AC420(None), AC425(None), AC34(None))
      AC405(None).validate(boxRetriever) shouldBe Set.empty

      setupCurrentYearMocks(AC12(None), AC405(Some(1)), AC410(None), AC415(None), AC420(None), AC425(None), AC34(None))
      AC405(None).validate(boxRetriever) shouldBe Set.empty

      setupCurrentYearMocks(AC12(None), AC405(None), AC410(Some(1)), AC415(None), AC420(None), AC425(None), AC34(None))
      AC405(None).validate(boxRetriever) shouldBe Set.empty

      setupCurrentYearMocks(AC12(None), AC405(None), AC410(None), AC415(Some(1)), AC420(None), AC425(None), AC34(None))
      AC405(None).validate(boxRetriever) shouldBe Set.empty

      setupCurrentYearMocks(AC12(None), AC405(None), AC410(None), AC415(None), AC420(Some(1)), AC425(None), AC34(None))
      AC405(None).validate(boxRetriever) shouldBe Set.empty

      setupCurrentYearMocks(AC12(None), AC405(None), AC410(None), AC415(None), AC420(None), AC425(Some(1)), AC34(None))
      AC405(None).validate(boxRetriever) shouldBe Set.empty

      setupCurrentYearMocks(AC12(None), AC405(None), AC410(None), AC415(None), AC420(None), AC425(None), AC34(Some(1)))
      AC405(None).validate(boxRetriever) shouldBe Set.empty
    }

    "pass validation if all current inputs are empty, CoHo Only filing, and ACQ8161 is false" in {
      when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
      when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(false)))

      setupCurrentYearMocks(AC12(None), AC405(None), AC410(None), AC415(None), AC420(None), AC425(None), AC34(None))

      AC405(None).validate(boxRetriever) shouldBe Set()
    }
    "fail validation if all current inputs are empty, CoHo Only filing, and ACQ8161 is true" in {

      when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
      when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(true)))

      setupCurrentYearMocks(AC12(None), AC405(None), AC410(None), AC415(None), AC420(None), AC425(None), AC34(None))

      AC405(None).validate(boxRetriever) shouldBe Set(CtValidation(boxId = None, "error.profit.loss.one.box.required"))
    }
    "fail validation if all current inputs are empty, Joint filing, and ACQ8161 is false" in {

      when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(false)))

      setupCurrentYearMocks(AC12(None), AC405(None), AC410(None), AC415(None), AC420(None), AC425(None), AC34(None))

      AC405(None).validate(boxRetriever) shouldBe Set(CtValidation(boxId = None, "error.profit.loss.one.box.required"))
    }
    "fail validation if all current inputs are empty, Joint filing, and ACQ8161 is true" in {

      when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(true)))

      setupCurrentYearMocks(AC12(None), AC405(None), AC410(None), AC415(None), AC420(None), AC425(None), AC34(None))

      AC405(None).validate(boxRetriever) shouldBe Set(CtValidation(boxId = None, "error.profit.loss.one.box.required"))
    }

    "pass validation if dormant where there would otherwise be an error" in {
      when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(boxRetriever.acq8999()).thenReturn(ACQ8999(Some(true)))
      when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(true)))

      setupCurrentYearMocks(AC12(None), AC405(None), AC410(None), AC415(None), AC420(None), AC425(None), AC34(None))
      AC405(None).validate(boxRetriever) shouldBe Set.empty
    }
  }
}
