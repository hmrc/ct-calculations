/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.SummaryCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP259(value: Int) extends CtBoxIdentifier("Profits and gains from non-trading loan relationships (box 6)") with CtInteger

object CP259 extends Calculated[CP259, ComputationsBoxRetriever] with SummaryCalculator  {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP259 =
   calculateProfitsAndGainsFromNonTradingLoanRelationships(fieldValueRetriever.cp43())

}
