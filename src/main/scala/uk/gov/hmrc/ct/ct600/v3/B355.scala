/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.losses
import uk.gov.hmrc.ct.computations.nir.NorthernIrelandCalculations
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

case class B355(value: Option[BigDecimal]) extends CtBoxIdentifier(name = "Second Rate Of Tax FY1") with AnnualConstant with CtOptionalBigDecimal

object B355 extends NorthernIrelandCalculations with Calculated[B355, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B355 = {
    val opt = if (losses.northernIrelandJourneyActive(fieldValueRetriever))
      nIRrateOfTaxFy1(fieldValueRetriever.cp1())
    else None
    B355(opt)
  }
}
