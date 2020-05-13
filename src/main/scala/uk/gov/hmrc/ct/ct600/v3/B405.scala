

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.losses
import uk.gov.hmrc.ct.computations.nir.NorthernIrelandCalculations
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

case class B405(value: Option[BigDecimal]) extends CtBoxIdentifier(name = "Rate Of Tax FY1") with AnnualConstant with CtOptionalBigDecimal

object B405 extends NorthernIrelandCalculations with Calculated[B405, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B405 = {
    val opt = if (losses.northernIrelandJourneyActive(fieldValueRetriever))
      nIRrateOfTaxFy1(fieldValueRetriever.cp1())
    else None
    B405(opt)
  }
}
