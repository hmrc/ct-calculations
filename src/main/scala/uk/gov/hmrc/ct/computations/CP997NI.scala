/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.nir.NorthernIrelandRateValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP997NI (value: Option[Int]) extends CP997Abstract(value)

object CP997NI extends NorthernIrelandRateValidation with Calculated[CP997NI, ComputationsBoxRetriever] {

  override def calculate(boxRetriever: ComputationsBoxRetriever): CP997NI = {
    if (boxRetriever.cato01().value > 0 && nirActiveForCurrentAccountingPeriod(boxRetriever)) {
      CP997NI(
        if (mayHaveNirLosses(boxRetriever)) Some(boxRetriever.cp997d().orZero + boxRetriever.cp997e().orZero)

        else boxRetriever.cp997d().value
      )
    } else CP997NI(None)
  }

}
