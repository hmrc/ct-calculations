/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.machineryAndPlant

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP109(value: Int) extends CtBoxIdentifier(name = "Total expenditure qualifying for special rate") with CtInteger {}

object CP109 extends Calculated[CP109, ComputationsBoxRetriever] with MachineryAndPlantCalculationsLogic {
  override def calculate(boxRetriever: ComputationsBoxRetriever): CP109 = {
    val carsQualifyingForSpecialRate = boxRetriever.cpAux3().value
    val writtenDownValueForSpecialRatePoolBroughtForward=boxRetriever.cp666().value.getOrElse(0)
    val total = sumOf(carsQualifyingForSpecialRate, writtenDownValueForSpecialRatePoolBroughtForward)

    CP109(total)
  }
}

