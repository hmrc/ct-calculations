/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

case class B300(value: Int) extends CtBoxIdentifier("Profits chargeable to Corporation Tax") with CtInteger

object B300 extends CorporationTaxCalculator with Calculated[B300, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B300 =
    calculateProfitsChargeableToCorporationTax(fieldValueRetriever.b235(), fieldValueRetriever.b275(), fieldValueRetriever.b285())
}
