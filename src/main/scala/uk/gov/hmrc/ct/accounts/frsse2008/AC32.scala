/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frsse2008

import uk.gov.hmrc.ct.accounts.frsse2008.calculations.ProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC32(value: Option[Int]) extends CtBoxIdentifier(name = "Current Profit or loss on ordinary activities before taxation") with CtOptionalInteger

object AC32 extends Calculated[AC32, Frsse2008AccountsBoxRetriever with FilingAttributesBoxValueRetriever] with ProfitOrLossCalculator {
  override def calculate(boxRetriever: Frsse2008AccountsBoxRetriever with FilingAttributesBoxValueRetriever): AC32 = {
    calculateCurrentProfitOrLossBeforeTax(ac26 = boxRetriever.ac26(),
                                          ac28 = boxRetriever.ac28(),
                                          ac30 = boxRetriever.ac30())
  }
}
