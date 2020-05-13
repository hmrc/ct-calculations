/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.ProfitAndLossCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP14(value: Int) extends CtBoxIdentifier(name = "Gross profit or loss") with CtInteger

object CP14 extends Calculated[CP14, ComputationsBoxRetriever] with ProfitAndLossCalculator {

  override def calculate(boxRetriever: ComputationsBoxRetriever): CP14 =
    calculateProfitOrLoss(boxRetriever.cp7, boxRetriever.cp8)
}
