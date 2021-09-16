/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations.{CP79, CP81, CPAux1}

trait TotalExpenditureQualifyingForTheFirstYearAllowanceCalculator extends CtTypeConverters {

  def totalExpenditureQualifyingForTheFirstYearAllowance(cp79: CP79, cpAux1: CPAux1): CP81 = {
    val result  = cp79 + cpAux1
    CP81(result)
  }
}
