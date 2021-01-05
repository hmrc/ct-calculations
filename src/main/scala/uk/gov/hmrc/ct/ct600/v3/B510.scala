/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

case class B510(value: BigDecimal) extends CtBoxIdentifier("Tax chargeable") with CtBigDecimal

object B510 extends CorporationTaxCalculator with Calculated[B510, CT600BoxRetriever] {
  override def calculate(boxRetriever: CT600BoxRetriever): B510 = {
    calculateTaxChargeable(boxRetriever.b475(), boxRetriever.b480())
  }
}
