

package uk.gov.hmrc.ct.accounts.approval.boxes

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class HmrcAccountsApproval(ac199A: List[AC199A] = List.empty, ac8092: List[AC8092] = List.empty, ac8091: AC8091, ac198A: AC198A) extends CtBoxIdentifier(name = "Accounts approval")
  with CtValue[HmrcAccountsApproval]
  with AccountsApproval {

  override def value = this

  override def approvalEnabled(boxRetriever: FilingAttributesBoxValueRetriever) = boxRetriever.hmrcAccountsApprovalRequired().value
}
