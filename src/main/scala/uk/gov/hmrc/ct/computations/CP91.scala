/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.MachineryAndPlantCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP91(value: Option[Int]) extends CtBoxIdentifier(name = "Balancing charge") with CtOptionalInteger

object CP91 extends Calculated[CP91, ComputationsBoxRetriever] with MachineryAndPlantCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP91 = {
    computeBalancingCharge(cpq8 = fieldValueRetriever.cpQ8(),
                           cp78 = fieldValueRetriever.cp78(),
                           cp82 = fieldValueRetriever.cp82(),
                           cp84 = fieldValueRetriever.cp84(),
                           cp666 = fieldValueRetriever.cp666(),
                           cp667 = fieldValueRetriever.cp667(),
                           cp672 = fieldValueRetriever.cp672(),
                           cp673 = fieldValueRetriever.cp673(),
                           cp674 = fieldValueRetriever.cp674(),
                           cpAux1 = fieldValueRetriever.cpAux1(),
                           cpAux2 = fieldValueRetriever.cpAux2(),
                           cpAux3 = fieldValueRetriever.cpAux3(),
                           cato20 = fieldValueRetriever.cato20()
    )
  }
}
