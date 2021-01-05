/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.MachineryAndPlantCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP92(value: Option[Int]) extends CtBoxIdentifier(name = "Written down value brought forward") with CtOptionalInteger

object CP92 extends Calculated[CP92, ComputationsBoxRetriever] with MachineryAndPlantCalculator {

  override def calculate(boxRetriever: ComputationsBoxRetriever): CP92 = {
    writtenDownValue(cpq8 = boxRetriever.cpQ8(),
                     cp78 = boxRetriever.cp78(),
                     cp82 = boxRetriever.cp82(),
                     cp89 = boxRetriever.cp89(),
                     cp91 = boxRetriever.cp91(),
                     cp672 = boxRetriever.cp672(),
                     cato20 = boxRetriever.cato20(),
                     cpAux2 = boxRetriever.cpAux2())
  }
}
