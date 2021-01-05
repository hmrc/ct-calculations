/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.approval.retriever

import uk.gov.hmrc.ct.accounts.approval.boxes.{CompaniesHouseAccountsApproval, HmrcAccountsApproval}
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait CoHoAccountsApprovalBoxRetriever extends AccountsBoxRetriever {
  self: FilingAttributesBoxValueRetriever =>

  def companiesHouseAccountsApproval(): CompaniesHouseAccountsApproval
}
