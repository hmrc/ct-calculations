/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.machineryAndPlant

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP105(value: Int) extends CtBoxIdentifier(name = "Total expenditure qualifying for main rate") with CtInteger {}

object CP105 extends Calculated[CP105, ComputationsBoxRetriever] {
  override def calculate(boxRetriever: ComputationsBoxRetriever): CP105 = {
    val carsQualifyingForMainRate = boxRetriever.cpAux2().value
    val mainRateExpenditureOtherThanCars = boxRetriever.cp82().value.getOrElse(0)

    CP105(carsQualifyingForMainRate + mainRateExpenditureOtherThanCars)
  }
}

