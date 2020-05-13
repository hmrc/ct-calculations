

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.IntangibleAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._

case class AC121(value: Option[Int]) extends CtBoxIdentifier(name = "Amortisation at [POA END]")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs102AccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {

    collectErrors(
      validateMoney(value, min = 0)
    )
  }
}

object AC121 extends Calculated[AC121, Frs102AccountsBoxRetriever]
  with IntangibleAssetsCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC121 = {
    boxRetriever match {
      case x: AbridgedAccountsBoxRetriever => calculateAbridgedAC121(x.ac118(), x.ac119(), x.ac120(), x.ac211())
      case x: FullAccountsBoxRetriever => calculateFullAC121(x.ac121A(), x.ac121B())
    }
  }

}
