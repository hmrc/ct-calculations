/*
 * Copyright 2021 HM Revenue & Customs
 *
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
