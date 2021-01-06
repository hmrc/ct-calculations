/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frsse2008

import uk.gov.hmrc.ct.accounts.frsse2008.calculations.ProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC36(value: Option[Int]) extends CtBoxIdentifier(name = "Current Profit or loss for the financial year") with CtOptionalInteger

object AC36 extends Calculated[AC36, Frsse2008AccountsBoxRetriever] with ProfitOrLossCalculator {
  override def calculate(boxRetriever: Frsse2008AccountsBoxRetriever): AC36 = {
    calculateCurrentProfitOtLossAfterTax(ac32 = boxRetriever.ac32(),
                                         ac34 = boxRetriever.ac34())
  }
}
