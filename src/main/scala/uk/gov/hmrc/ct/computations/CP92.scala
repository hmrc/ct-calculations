/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.MachineryAndPlantCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP92(value: Option[Int]) extends CtBoxIdentifier(name = "Written down value brought forward") with CtOptionalInteger

object CP92 extends Calculated[CP92, ComputationsBoxRetriever] with MachineryAndPlantCalculator {


  override def calculate(boxRetriever: ComputationsBoxRetriever): CP92 = {
    import boxRetriever._
    writtenDownValue(cpq8 = cpQ8(),
                     cp78 = cp78(),
                     cp79 = cp79(),
                     cp82 = cp82(),
                     cp83 = cp83(),
                     cp89 = cp89(),
                     cp91 = cp91(),
                     cp672 = cp672(),
                     cato20 = cato20(),
                     cpAux1 = cpAux1(),
                     cpAux2 = cpAux2())
  }
}
