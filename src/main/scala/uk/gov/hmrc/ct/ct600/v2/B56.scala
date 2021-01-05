/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.v2.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v2.retriever.CT600BoxRetriever

case class B56(value: BigDecimal) extends CtBoxIdentifier("Tax") with CtBigDecimal

object B56 extends CorporationTaxCalculator with Calculated[B56, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B56 =
    corporationTaxFy2(
      fieldValueRetriever.b54(),
      fieldValueRetriever.b55())
}
