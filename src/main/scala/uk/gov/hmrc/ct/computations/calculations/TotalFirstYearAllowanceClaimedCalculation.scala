/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations.{CP85, CP86, CP87}

trait TotalFirstYearAllowanceClaimedCalculation extends CtTypeConverters {

  def totalFirstYearAllowanceClaimedCalculation(cp85: CP85, cp86: CP86): CP87 = {
    val result = cp85 + cp86
    CP87(result)
  }
}
