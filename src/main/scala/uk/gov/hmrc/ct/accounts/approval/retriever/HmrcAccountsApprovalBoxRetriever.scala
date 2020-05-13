

package uk.gov.hmrc.ct.accounts.approval.retriever

import uk.gov.hmrc.ct.accounts.approval.boxes.HmrcAccountsApproval
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait HmrcAccountsApprovalBoxRetriever extends AccountsBoxRetriever {
  self: FilingAttributesBoxValueRetriever =>

  def hmrcAccountsApproval(): HmrcAccountsApproval
}
