/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frsse2008

import uk.gov.hmrc.ct.accounts.frsse2008.calculations.ProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC27(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Operating profit or loss") with CtOptionalInteger

object AC27 extends Calculated[AC27, Frsse2008AccountsBoxRetriever] with ProfitOrLossCalculator {
  override def calculate(boxRetriever: Frsse2008AccountsBoxRetriever): AC27 = {
    calculatePreviousOperatingProfitOrLoss(ac17= boxRetriever.ac17(),
                                           ac19 = boxRetriever.ac19(),
                                           ac21 = boxRetriever.ac21(),
                                           ac23 = boxRetriever.ac23())
  }
}
