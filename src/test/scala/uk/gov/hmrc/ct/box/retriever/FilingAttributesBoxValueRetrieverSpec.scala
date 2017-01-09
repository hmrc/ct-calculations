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

package uk.gov.hmrc.ct.box.retriever

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValue
import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever
import uk.gov.hmrc.ct._
import uk.gov.hmrc.ct.domain.CompanyTypes

class FilingAttributesBoxValueRetrieverSpec extends WordSpec with Matchers {

  "FilingAttributesBoxValueRetriever" should {
    "have 14 functions" in {
      BoxValues.boxIdFunctions(classOf[FilingAttributesBoxValueRetriever]).size shouldBe 14
    }

    "get ct values" in {

      val retriever = new FilingAttributesBoxValueRetrieverForTest
      val result = BoxValues.generateValues(retriever)
      result("FilingCompanyType") shouldBe retriever.companyType()
      result("UTR") shouldBe retriever.utr()

      result("AbbreviatedAccountsFiling") shouldBe retriever.abbreviatedAccountsFiling()
      result("StatutoryAccountsFiling") shouldBe retriever.statutoryAccountsFiling()
      result("MicroEntityFiling") shouldBe retriever.microEntityFiling()
      result("AbridgedFiling") shouldBe retriever.abridgedFiling()
      result("CompaniesHouseFiling") shouldBe retriever.companiesHouseFiling()
      result("HMRCFiling") shouldBe retriever.hmrcFiling()
      result("CompaniesHouseSubmitted") shouldBe retriever.companiesHouseSubmitted()
      result("HMRCSubmitted") shouldBe retriever.hmrcSubmitted()
      result("HMRCAmendment") shouldBe retriever.hmrcAmendment()
      result("CountryOfRegistration") shouldBe retriever.countryOfRegistration()
      result("CoHoAccountsApprovalRequired") shouldBe retriever.coHoAccountsApprovalRequired()
      result("HmrcAccountsApprovalRequired") shouldBe retriever.hmrcAccountsApprovalRequired()
    }
  }
}

class FilingAttributesBoxValueRetrieverForTest extends FilingAttributesBoxValueRetriever {

  override def generateValues: Map[String, CtValue[_]] = ???

  override def companyType(): FilingCompanyType = FilingCompanyType(CompanyTypes.UkTradingCompany)

  override def utr(): UTR = UTR("123456")

  override def abbreviatedAccountsFiling(): AbbreviatedAccountsFiling = AbbreviatedAccountsFiling(false)

  override def statutoryAccountsFiling(): StatutoryAccountsFiling = StatutoryAccountsFiling(true)

  override def microEntityFiling(): MicroEntityFiling = MicroEntityFiling(false)

  override def abridgedFiling(): AbridgedFiling = AbridgedFiling(false)

  override def companiesHouseFiling(): CompaniesHouseFiling = CompaniesHouseFiling(true)

  override def hmrcFiling(): HMRCFiling = HMRCFiling(true)

  override def companiesHouseSubmitted(): CompaniesHouseSubmitted = CompaniesHouseSubmitted(false)

  override def hmrcSubmitted(): HMRCSubmitted = HMRCSubmitted(true)

  override def hmrcAmendment(): HMRCAmendment = HMRCAmendment(false)

  override def countryOfRegistration(): CountryOfRegistration = CountryOfRegistration.NorthernIreland

  override def coHoAccountsApprovalRequired(): CoHoAccountsApprovalRequired = CoHoAccountsApprovalRequired(true)

  override def hmrcAccountsApprovalRequired(): HmrcAccountsApprovalRequired = HmrcAccountsApprovalRequired(false)
}
