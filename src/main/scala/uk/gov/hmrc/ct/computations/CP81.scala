/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.calculations.TotalExpenditureQualifyingForTheFirstYearAllowanceCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP81(value: Int) extends CtBoxIdentifier(name = "Total expenditure qualifying for the first year allowance (FYA)") with CtInteger

object CP81 extends Calculated[CP81, ComputationsBoxRetriever] with CtTypeConverters with TotalExpenditureQualifyingForTheFirstYearAllowanceCalculator {

  override def calculate(retriever: ComputationsBoxRetriever) = {
    totalExpenditureQualifyingForTheFirstYearAllowance(cp79 = retriever.cp79, cp80 = retriever.cp80) // change to cpAUX1
  }
}
