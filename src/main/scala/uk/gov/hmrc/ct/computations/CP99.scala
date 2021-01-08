/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.SummaryCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP99(value: Int) extends CtBoxIdentifier("Trade net allowances") with CtInteger

object CP99 extends Calculated[CP99, ComputationsBoxRetriever] with SummaryCalculator  {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP99 =
    calculateTradeNetAllowancesForSummary(fieldValueRetriever.cp186(),
                                          fieldValueRetriever.cp668(),
                                          fieldValueRetriever.cp674(),
                                          fieldValueRetriever.cp91(),
                                          fieldValueRetriever.cp670())

}
