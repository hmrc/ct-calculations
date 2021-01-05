/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.SummaryCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP258(value: Int) extends CtBoxIdentifier("Net trading and professional profits (box 5)") with CtInteger

object CP258 extends Calculated[CP258, ComputationsBoxRetriever] with SummaryCalculator  {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP258 =
    calculateNetTradingAndProfessionalProfits(fieldValueRetriever.cp256(), fieldValueRetriever.cp257())

}
