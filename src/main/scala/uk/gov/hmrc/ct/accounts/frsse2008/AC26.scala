/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frsse2008

import uk.gov.hmrc.ct.accounts.frsse2008.calculations.ProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC26(value: Option[Int]) extends CtBoxIdentifier(name = "Current Operating profit or loss") with CtOptionalInteger

object AC26 extends Calculated[AC26, Frsse2008AccountsBoxRetriever] with ProfitOrLossCalculator {
  override def calculate(boxRetriever: Frsse2008AccountsBoxRetriever): AC26 = {
    calculateCurrentOperatingProfitOrLoss(boxRetriever.ac16(),
                                          ac18 = boxRetriever.ac18(),
                                          ac20 = boxRetriever.ac20(),
                                          ac22 = boxRetriever.ac22())
  }
}
