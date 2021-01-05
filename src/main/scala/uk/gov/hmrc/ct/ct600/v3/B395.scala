/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

// was B56
case class B395(value: BigDecimal) extends CtBoxIdentifier("Tax FY2") with CtBigDecimal

object B395 extends CorporationTaxCalculator with Calculated[B395, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B395 = {
    calculateTaxForSecondFinancialYear(fieldValueRetriever.b385(), fieldValueRetriever.b390())
  }
}
