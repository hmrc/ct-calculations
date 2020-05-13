

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever._

case class ACQ5031(value: Option[Boolean]) extends CtBoxIdentifier(name = "Land and buildings")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[FullAccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: FullAccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._
    collectErrors(
      cannotExistErrorIf(hasValue && ac44.noValue && ac45.noValue),

      failIf(anyHaveValue(ac44, ac45)) {
        atLeastOneBoxHasValue("balance.sheet.tangible.assets", this, acq5032, acq5033, acq5034, acq5035)
      }
    )
  }
}
