/*
 * Copyright 2021 HM Revenue & Customs
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

import org.joda.time.LocalDate
import org.mockito.Mockito.when
import uk.gov.hmrc.ct.accounts._
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.domain.CompanyTypes.UkTradingCompany
import uk.gov.hmrc.ct.utils.CatoInputBounds._
import uk.gov.hmrc.ct.utils.UnitSpec
import uk.gov.hmrc.ct.{AbridgedFiling, CATO24, CompaniesHouseFiling, FilingCompanyType, HMRCFiling}

class AC16Spec extends AccountsMoneyValidationFixture[Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever] with UnitSpec with MockFrs102AccountsRetriever {

  private def doMocks(hmrcFiling: Boolean, abridged:Boolean = true, cato24:Boolean = true): Unit = {
    when(boxRetriever.ac3()).thenReturn(AC3(new LocalDate("2019-09-01")))
    when(boxRetriever.ac4()).thenReturn(AC4(new LocalDate("2020-08-31")))
    when(boxRetriever.companyType()).thenReturn(FilingCompanyType(UkTradingCompany))
    if (hmrcFiling) {
      when(boxRetriever.hmrcFiling()) thenReturn HMRCFiling(true)
      when(boxRetriever.companiesHouseFiling()) thenReturn CompaniesHouseFiling(false)
    } else {
      when(boxRetriever.hmrcFiling()) thenReturn HMRCFiling(false)
      when(boxRetriever.companiesHouseFiling()) thenReturn CompaniesHouseFiling(true)
    }
    when(boxRetriever.abridgedFiling()) thenReturn AbridgedFiling(abridged)
    when(boxRetriever.cato24()) thenReturn CATO24(Some(cato24))
  }

  private val ac16 = "AC16"


  "AC16" should {
    "fail validation" when {
      "its value is greater than the apportioned max turnover and the user is going through an hmrc journey" in {
        doMocks(true)
        AC16(Some(turnoverHMRCMaxValue632k + 1)).validate(boxRetriever) shouldBe Set(CtValidation(
          boxId = Some(ac16),
          errorMessageKey = s"error.$ac16.hmrc.turnover.above.max",
          args = Some(List(oldMinWithCommas, turnoverHMRCMaxWithCommas))))
      }

      "its value is greater than the apportioned max turnover user is going through an coho journey" in {
        doMocks(false)
        AC16(Some(turnoverCOHOMaxValue10m + 1)).validate(boxRetriever) shouldBe Set(CtValidation(
          boxId = Some(ac16),
          errorMessageKey = s"error.$ac16.coho.turnover.above.max",
          args = Some(List("-" + turnoverCOHOMaxWithCommas, turnoverCOHOMaxWithCommas))))
      }

      "its not an abridged company" in {
        doMocks(true, abridged = false)
        AC16(Some(turnoverHMRCMaxValue632k)).validate(boxRetriever) shouldBe fieldRequiredError(ac16)
      }

      "its select off payroll working" in {
       doMocks(true, cato24 = false)
        AC16(Some(turnoverHMRCMaxValue632k)).validate(boxRetriever) shouldBe fieldRequiredError(ac16)
      }
    }
    "pass validation" when {
      "its value is less than the apportioned max turnover and the user is going through an hmrc journey" in {
        doMocks(true)
        AC16(Some(turnoverHMRCMaxValue632k)).validate(boxRetriever) shouldBe validationSuccess
      }
      "its value is less than the apportioned max turnover user is going through an coho journey" in {
        doMocks(false)
        AC16(Some(turnoverCOHOMaxValue10m)).validate(boxRetriever) shouldBe validationSuccess
      }

    }
  }
}
