/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.LossesSetAgainstOtherProfitsCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP998(value: Option[Int]) extends CtBoxIdentifier(name = "Losses this AP set against other profits this AP") with CtOptionalInteger

object CP998 extends Calculated[CP998, ComputationsBoxRetriever] with LossesSetAgainstOtherProfitsCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP998 = {
    calculateLossesSetAgainstProfits(cato01 = fieldValueRetriever.cato01(),
                                     cp997 = fieldValueRetriever.chooseCp997(),
                                     cp118 = fieldValueRetriever.cp118(),
                                     cpq19 = fieldValueRetriever.cpQ19())
  }

}
