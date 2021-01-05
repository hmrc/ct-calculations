/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.NetProfitsChargeableToCtCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

case class CP295(value: Int)
  extends CtBoxIdentifier(name = "Profits chargeable to CT")
    with CtInteger

object CP295 extends Calculated[CP295, ComputationsBoxRetriever] with NetProfitsChargeableToCtCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP295 = {
    calculateNetProfitsChargeableToCt(fieldValueRetriever.cp293(),
      fieldValueRetriever.cp294(),
      fieldValueRetriever.chooseCp997(),
      fieldValueRetriever.cp999())
  }

}
