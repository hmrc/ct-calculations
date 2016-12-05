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

package uk.gov.hmrc.ct.accounts.frs10x.boxes.accountsApproval

import org.mockito.Mockito._
import uk.gov.hmrc.ct.accounts.frs10x.boxes.{AC8021, ACQ8161}
import uk.gov.hmrc.ct.accounts.{MockFrs102AccountsRetriever, MockFrs10xAccountsRetriever}
import uk.gov.hmrc.ct.{CompaniesHouseFiling, HMRCFiling}

class sHmrcAccountsApprovalSpec extends AccountsApprovalFixture with MockFrs10xAccountsRetriever {

  override def setUpMocks(): Unit = {
    when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
    when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
    when(boxRetriever.ac8021()).thenReturn(AC8021(Some(false)))
    when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(false)))
  }

  override def setUpDisabledMocks(): Unit = {
    when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
  }

  testAccountsApproval("HmrcAccountsApproval", HmrcAccountsApproval.apply)

  "HMRCAccountsApproval" should {
    val emptyApproval = HmrcAccountsApproval(List.empty, List.empty, AC8091(None), AC198A(None))

    "have display condition true for HMRC Only filings" in {

      when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(false))
      when(boxRetriever.ac8021()).thenReturn(AC8021(None))
      when(boxRetriever.acq8161()).thenReturn(ACQ8161(None))

      emptyApproval.approvalEnabled(boxRetriever) shouldBe true
    }

    "have display condition true for Joint filings if AC8021 is false and ACQ8161 is true" in {

      when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(boxRetriever.ac8021()).thenReturn(AC8021(Some(false)))
      when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(true)))

      emptyApproval.approvalEnabled(boxRetriever) shouldBe true
    }

    "have display condition true for Joint filings if AC8021 is true and ACQ8161 is false" in {

      when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(boxRetriever.ac8021()).thenReturn(AC8021(Some(true)))
      when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(false)))

      emptyApproval.approvalEnabled(boxRetriever) shouldBe true
    }

    "have display condition true for Joint filings if both AC8021 and ACQ8161 are false" in {

      when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(boxRetriever.ac8021()).thenReturn(AC8021(Some(false)))
      when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(false)))

      emptyApproval.approvalEnabled(boxRetriever) shouldBe true
    }

    "have display condition true for Joint filings if AC8021 is None and ACQ8161 is false" in {

      when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(boxRetriever.ac8021()).thenReturn(AC8021(None))
      when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(false)))

      emptyApproval.approvalEnabled(boxRetriever) shouldBe true
    }

    "have display condition false for Joint filings if both AC8021 and ACQ8161 are true" in {

      when(boxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(boxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(boxRetriever.ac8021()).thenReturn(AC8021(Some(true)))
      when(boxRetriever.acq8161()).thenReturn(ACQ8161(Some(true)))

      emptyApproval.approvalEnabled(boxRetriever) shouldBe false
    }
  }

}
