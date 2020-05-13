

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoolean, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.calculations.PeriodOfAccountsCalculator

case class RSQ3(value: Boolean) extends CtBoxIdentifier with CtBoolean

object RSQ3  extends Calculated[RSQ3, AccountsBoxRetriever] with PeriodOfAccountsCalculator {

  override def calculate(boxRetriever: AccountsBoxRetriever) =
    RSQ3(isLongPeriodOfAccounts(boxRetriever.ac3(), boxRetriever.ac4()))

}
