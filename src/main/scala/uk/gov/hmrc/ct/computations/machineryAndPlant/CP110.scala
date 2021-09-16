/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.machineryAndPlant

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP110(value: Int) extends CtBoxIdentifier(name = "AIA not claimed") with CtInteger

object CP110 extends Calculated[CP110, ComputationsBoxRetriever] with MachineryAndPlantCalculationsLogic {
  override def calculate(boxRetriever: ComputationsBoxRetriever): CP110 = {
    val expenditureQualifyingForAIA = boxRetriever.cp83()
    val annualInvestmentAllowance = boxRetriever.cp88()
    val total = sumOf(expenditureQualifyingForAIA, -annualInvestmentAllowance)

    CP110(total)
  }
}

