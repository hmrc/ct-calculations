/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v2.calculations

import uk.gov.hmrc.ct.ct600e.v2.{E24e, E24eA, E24eB}

trait LoansAndDebtorsCalculator {
  def calculateFieldValue(e24eA: E24eA, e24eB: E24eB): E24e = {
    val fields = Seq(e24eA, e24eB)

    if (fields.exists(_.hasValue))
      E24e(Some(fields.map(_.orZero).sum))
    else
      E24e(None)
  }
}
