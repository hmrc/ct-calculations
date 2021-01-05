/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._

trait ProfitAndLossCalculator extends CtTypeConverters {

  def calculateProfitOrLoss(cp7: CP7, cp8: CP8, cp981: CP981, cp983: CP983): CP14 = CP14(cp7 + cp983 - cp981 - cp8)

  def calculateGrossProfitOrLossBeforeTax(cp14: CP14, cp40: CP40, cp43: CP43, cp501: CP501, cp502: CP502): CP44 = {
      CP44(cp14 - cp40 + cp502 + cp501 + cp43)
  }

}
