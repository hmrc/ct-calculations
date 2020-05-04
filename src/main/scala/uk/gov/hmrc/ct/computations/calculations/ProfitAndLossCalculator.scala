/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.accounts.frs105.boxes.AC415
import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._

trait ProfitAndLossCalculator extends CtTypeConverters {

  def calculateProfitOrLoss(cp7: CP7, cp8: CP8): CP14 = CP14(cp7 minus cp8)

  def calculateGrossProfitOrLossBeforeTax(cp14: CP14, cp40: CP40, cp43: CP43, cp509: CP509, cp502: CP502, cp981: CP981, cp982: CP982, ac415: AC415): CP44 = {
    if(cp981.value.nonEmpty)
      CP44(cp14 - cp40 + cp502 + cp509 + cp43 - (cp981 + cp982 + ac415))
    else
      CP44(cp14 - cp40 + cp502 + cp509 + cp43)
  }

}
