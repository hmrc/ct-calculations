

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC8082(value: Option[Boolean]) extends CtBoxIdentifier(name = "The members have not required the company to obtain an audit in accordance with section 476 of the Companies Act 2006.")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[Frs10xAccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs10xAccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateBooleanAsTrue("AC8082", this)
    )
  }
  
}
