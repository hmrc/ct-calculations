/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

case class E1003(value: Int) extends CtBoxIdentifier("First Financial Year") with CtInteger

object E1003 extends CorporationTaxCalculator with Calculated[E1003, CT600EBoxRetriever] {

  override def calculate(fieldValueRetriever: CT600EBoxRetriever): E1003 = {
    E1003(financialYear1(HmrcAccountingPeriod(fieldValueRetriever.e3(), fieldValueRetriever.e4)))
  }
}
