/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator

case class B330(value: Int) extends CtBoxIdentifier("Financial Year FY1") with CtInteger

// was B43
object B330 extends CorporationTaxCalculator with Calculated[B330, ComputationsBoxRetriever] {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): B330 =
      B330(financialYear1(
        HmrcAccountingPeriod(fieldValueRetriever.cp1(), fieldValueRetriever.cp2())
      ))
}
