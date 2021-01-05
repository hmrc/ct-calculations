/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frsse2008

import uk.gov.hmrc.ct.accounts.frsse2008.calculations.ProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC41(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Net balance transferred to reserves") with CtOptionalInteger

object AC41 extends Calculated[AC41, Frsse2008AccountsBoxRetriever] with ProfitOrLossCalculator {
  override def calculate(boxRetriever: Frsse2008AccountsBoxRetriever): AC41 = {
    calculatePreviousNetBalance(boxRetriever.ac37(), boxRetriever.ac39())
  }
}
