/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoolean, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.calculations.PeriodOfAccountsCalculator

case class B50(value: Boolean) extends CtBoxIdentifier("Making more then one return for this company") with CtBoolean

object B50 extends Calculated[B50, AccountsBoxRetriever] with PeriodOfAccountsCalculator {

  override def calculate(boxRetriever: AccountsBoxRetriever) =
    B50(isLongPeriodOfAccounts(boxRetriever.ac3(), boxRetriever.ac4()))

}
