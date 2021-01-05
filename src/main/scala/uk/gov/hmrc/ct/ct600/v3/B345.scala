/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

// was B46
case class B345(value: BigDecimal) extends CtBoxIdentifier("Tax for rate: B340 FY1") with CtBigDecimal

object B345 extends CorporationTaxCalculator with Calculated[B345, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B345 = {
    calculateTaxForFirstFinancialYear(fieldValueRetriever.b335(), fieldValueRetriever.b340())
  }

}
