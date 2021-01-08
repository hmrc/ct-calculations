/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations.{CP79, CP80, CP81}

trait TotalExpenditureQualifyingForTheFirstYearAllowanceCalculator extends CtTypeConverters {

  def totalExpenditureQualifyingForTheFirstYearAllowance(cp79: CP79, cp80: CP80): CP81 = {
    val result  = cp79 + cp80 //cpaux1
    CP81(result)
  }
}
