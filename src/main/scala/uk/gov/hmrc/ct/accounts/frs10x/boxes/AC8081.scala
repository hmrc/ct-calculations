

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.frs10x.retriever.{Frs10xAccountsBoxRetriever, Frs10xDormancyBoxRetriever}
import uk.gov.hmrc.ct.box._

case class AC8081(value: Option[Boolean]) extends CtBoxIdentifier(name = "For the year ending <<POA END DATE>> the company was entitled to exemption under section 477 of the Companies Act 2006 relating to small companies.")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[Frs10xAccountsBoxRetriever with Frs10xDormancyBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs10xAccountsBoxRetriever with Frs10xDormancyBoxRetriever): Set[CtValidation] = {
    failIf(!boxRetriever.acq8999().orFalse) (
      validateBooleanAsTrue("AC8081", this)
    )
  }

}
