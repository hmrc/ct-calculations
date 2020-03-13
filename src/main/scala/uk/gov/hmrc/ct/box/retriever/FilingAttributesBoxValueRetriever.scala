/*
 * Copyright 2020 HM Revenue & Customs
 *
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
