/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.TotalProfitsBeforeDeductionsCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP293(value: Int) extends CtBoxIdentifier("Total profits before other deductions and reliefs") with CtInteger

object CP293 extends Calculated[CP293, ComputationsBoxRetriever] with TotalProfitsBeforeDeductionsCalculator  {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP293 =
   computeTotalProfitsBeforeDeductionsAndReliefs(fieldValueRetriever.cp284(),
                                                 fieldValueRetriever.cp58(),
                                                 fieldValueRetriever.cp511(),
                                                 fieldValueRetriever.cp502())

}
