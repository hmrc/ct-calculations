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

package uk.gov.hmrc.ct.box.retriever

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValue
import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever
import uk.gov.hmrc.ct._
import uk.gov.hmrc.ct.domain.CompanyTypes

class FilingAttributesBoxValueRetrieverSpec extends WordSpec with Matchers {

  "FilingAttributesBoxValueRetriever" should {
    "have 10 functions" in {
      FilingAttributesBoxValueRetriever.retrieveBoxIdFunctions(classOf[FilingAttributesBoxValueRetriever]).size shouldBe 10
    }

    "get ct values" in {

      val retriever = new FilingAttributesBoxValueRetrieverForTest
      val result = FilingAttributesBoxValueRetriever.generateValues(retriever)
      result("ProductName") shouldBe retriever.retrieveProductName()
      result("FilingCompanyType") shouldBe retriever.retrieveCompanyType()
      result("UTR") shouldBe retriever.retrieveUTR()

      result("AbbreviatedAccountsFiling") shouldBe retriever.retrieveAbbreviatedAccountsFiling()
      result("StatutoryAccountsFiling") shouldBe retriever.retrieveStatutoryAccountsFiling()
      result("MicroEntityFiling") shouldBe retriever.retrieveMicroEntityFiling()
      result("AbridgedFiling") shouldBe retriever.retrieveAbridgedFiling()
      result("CompaniesHouseFiling") shouldBe retriever.retrieveCompaniesHouseFiling()
      result("HMRCFiling") shouldBe retriever.retrieveHMRCFiling()
      result("HMRCAmendment") shouldBe retriever.retrieveHMRCAmendment()
    }
  }
}

class FilingAttributesBoxValueRetrieverForTest extends FilingAttributesBoxValueRetriever {

  override def generateValues: Map[String, CtValue[_]] = ???

  override def retrieveProductName(): ProductName = ProductName("productType")

  override def retrieveCompanyType(): FilingCompanyType = FilingCompanyType(CompanyTypes.UkTradingCompany)

  override def retrieveUTR(): UTR = UTR("123456")

  override def retrieveAbbreviatedAccountsFiling(): AbbreviatedAccountsFiling = AbbreviatedAccountsFiling(false)

  override def retrieveStatutoryAccountsFiling(): StatutoryAccountsFiling = StatutoryAccountsFiling(true)

  override def retrieveMicroEntityFiling(): MicroEntityFiling = MicroEntityFiling(false)

  override def retrieveAbridgedFiling(): AbridgedFiling = AbridgedFiling(false)

  override def retrieveCompaniesHouseFiling(): CompaniesHouseFiling = CompaniesHouseFiling(true)

  override def retrieveHMRCFiling(): HMRCFiling = HMRCFiling(true)

  override def retrieveHMRCAmendment(): HMRCAmendment = HMRCAmendment(false)
}
