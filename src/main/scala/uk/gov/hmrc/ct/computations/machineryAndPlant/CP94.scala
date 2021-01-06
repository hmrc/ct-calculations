/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.machineryAndPlant

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP94(value: Int) extends CtBoxIdentifier(name = "Total expenditure qualifying for FYA") with CtInteger {}

object CP94 extends Calculated[CP94, ComputationsBoxRetriever]  with MachineryAndPlantCalculationsLogic {
  override def calculate(boxRetriever: ComputationsBoxRetriever): CP94 = {
    val carsQualifyingForFYA = boxRetriever.cpAux1()
    val fyaExpenditureOtherThanCars = boxRetriever.cp79()
    val total = sumOf(carsQualifyingForFYA, fyaExpenditureOtherThanCars)

    CP94(total)
  }
}
