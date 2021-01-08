/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._

trait TotalDeductionsCalculator extends CtTypeConverters {

  def totalDeductionsCalculation(cp58: CP58,
                                 cp505: CP505,
                                 cp507: CP507,
                                 cp55: CP55,
                                 cp57: CP57,
                                 cp983: CP983): CP59 = {
    CP59(cp58 + cp505 + cp507 + cp55 + cp57 + cp983)
  }
}
