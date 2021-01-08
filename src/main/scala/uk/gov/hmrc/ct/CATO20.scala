/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger, NotInPdf}
import uk.gov.hmrc.ct.computations.calculations.MachineryAndPlantCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CATO20(value: Int) extends CtBoxIdentifier(name = "UnclaimedAIA_FYA") with CtInteger with NotInPdf

object CATO20 extends Calculated[CATO20, ComputationsBoxRetriever] with MachineryAndPlantCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CATO20 = {
    unclaimedAIAFirstYearAllowance(cp81 = fieldValueRetriever.cp81(),
                                            cp83 = fieldValueRetriever.cp83(),
                                            cp87 = fieldValueRetriever.cp87(),
                                            cp88 = fieldValueRetriever.cp88(),
                                            cpAux1 = fieldValueRetriever.cpAux1())
  }
}
