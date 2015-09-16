package uk.gov.hmrc.ct.box.retriever

import uk.gov.hmrc.ct._

object FilingAttributesBoxValueRetriever extends BoxValues[FilingAttributesBoxValueRetriever]

trait FilingAttributesBoxValueRetriever extends BoxRetriever {

  def retrieveProductName(): ProductName

  def retrieveAbbreviatedAccountsFiling(): AbbreviatedAccountsFiling

  def retrieveAbridgedFiling(): AbridgedFiling

  def retrieveCompaniesHouseFiling(): CompaniesHouseFiling

  def retrieveHMRCFiling(): HMRCFiling

  def retrieveMicroEntityFiling(): MicroEntityFiling

  def retrieveStatutoryAccountsFiling(): StatutoryAccountsFiling
}
