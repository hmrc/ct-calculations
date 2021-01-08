/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.MachineryAndPlantCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP186(value: Option[Int]) extends CtBoxIdentifier(name = "Total Allowances") with CtOptionalInteger

object CP186 extends Calculated[CP186, ComputationsBoxRetriever] with MachineryAndPlantCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP186 = {
    computeTotalAllowancesClaimed(cpq8 = fieldValueRetriever.cpQ8(),
                                  cp87 = fieldValueRetriever.cp87(),
                                  cp88 = fieldValueRetriever.cp88(),
                                  cp89 = fieldValueRetriever.cp89(),
                                  cp90 = fieldValueRetriever.cp90())
  }

}
