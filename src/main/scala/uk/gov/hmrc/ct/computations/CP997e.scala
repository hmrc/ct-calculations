/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.nir.NorthernIrelandCalculations
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.calculations.Ct600AnnualConstants

case class CP997e(value: Option[Int])
  extends CtBoxIdentifier("Value of revalued NIR Losses offset against non trading profits this AP")
  with CtOptionalInteger

object CP997e extends Calculated[CP997e, ComputationsBoxRetriever] with NorthernIrelandCalculations {
  override def calculate(boxRetriever: ComputationsBoxRetriever): CP997e =
    apply(
      boxRetriever.cp997c(),
      boxRetriever.cp1(),
      boxRetriever.cp2(),
      boxRetriever.cpQ19()
    )
  def apply(cp997c: CP997c,
            cp1: CP1,
            cp2: CP2,
            cpq19: CPQ19): CP997e = {
    revaluedNirLossesAgainstNonTradingProfit(Ct600AnnualConstants)(
      cp997c,
      HmrcAccountingPeriod(cp1, cp2),
      cpq19
    )
  }

}
