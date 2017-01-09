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

import uk.gov.hmrc.ct._

trait FilingAttributesBoxValueRetriever extends BoxRetriever {

  def companyType(): FilingCompanyType

  def abbreviatedAccountsFiling(): AbbreviatedAccountsFiling

  def abridgedFiling(): AbridgedFiling

  def companiesHouseFiling(): CompaniesHouseFiling

  def hmrcFiling(): HMRCFiling

  def companiesHouseSubmitted(): CompaniesHouseSubmitted

  def hmrcSubmitted(): HMRCSubmitted

  def hmrcAmendment(): HMRCAmendment

  def microEntityFiling(): MicroEntityFiling

  def statutoryAccountsFiling(): StatutoryAccountsFiling

  def utr(): UTR

  def countryOfRegistration(): CountryOfRegistration

  def coHoAccountsApprovalRequired(): CoHoAccountsApprovalRequired = CoHoAccountsApprovalRequired(companiesHouseFiling())

  def hmrcAccountsApprovalRequired(): HmrcAccountsApprovalRequired = HmrcAccountsApprovalRequired.calculate(this)
}
