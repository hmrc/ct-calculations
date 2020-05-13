/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._

trait TotalDeductionsCalculator extends CtTypeConverters {

  def totalDeductionsCalculation(cp58: CP58,
                                 cp505: CP505,
                                 cp509: CP509,
                                 cp55: CP55,
                                 cp57: CP57): CP59 = {
    CP59(cp58 + cp505 + cp509 + cp55 + cp57)
  }
}
