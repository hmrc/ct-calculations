/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._


trait TotalAdditionsCalculator extends CtTypeConverters {

  def totalAdditionsCalculation(cp46: CP46,
                                cp47: CP47,
                                cp48: CP48,
                                cp49: CP49,
                                cp51: CP51,
                                cp52: CP52,
                                cp53: CP53): CP54 = {
    CP54(cp46 + cp47 + cp48 + cp49 + cp51 + cp52 + cp53)
  }
}
