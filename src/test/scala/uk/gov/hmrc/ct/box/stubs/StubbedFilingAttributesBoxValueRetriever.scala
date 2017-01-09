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

package uk.gov.hmrc.ct.box.stubs

import uk.gov.hmrc.ct._
import uk.gov.hmrc.ct.box.CtValue
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait StubbedFilingAttributesBoxValueRetriever extends FilingAttributesBoxValueRetriever {

  override def companyType(): FilingCompanyType = ???

  override def abbreviatedAccountsFiling(): AbbreviatedAccountsFiling = ???

  override def statutoryAccountsFiling(): StatutoryAccountsFiling = ???

  override def microEntityFiling(): MicroEntityFiling = ???

  override def abridgedFiling(): AbridgedFiling = ???

  override def hmrcFiling(): HMRCFiling = ???

  override def companiesHouseSubmitted(): CompaniesHouseSubmitted = ???

  override def hmrcSubmitted(): HMRCSubmitted = ???

  override def hmrcAmendment(): HMRCAmendment = ???

  override def companiesHouseFiling(): CompaniesHouseFiling = ???

  override def generateValues: Map[String, CtValue[_]] = ???

  override def utr(): UTR = ???

  override def countryOfRegistration(): CountryOfRegistration = ???

  override def coHoAccountsApprovalRequired(): CoHoAccountsApprovalRequired = ???

  override def hmrcAccountsApprovalRequired(): HmrcAccountsApprovalRequired = ???
}
