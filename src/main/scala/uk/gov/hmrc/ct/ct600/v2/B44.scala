/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.calculations.CorporationTaxCalculatorParameters
import uk.gov.hmrc.ct.ct600.v2.calculations.CorporationTaxCalculator

case class B44(value: Int) extends CtBoxIdentifier("Amount of profit") with CtInteger

object B44 extends CorporationTaxCalculator with Calculated[B44, ComputationsBoxRetriever] {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): B44 =
    calculateApportionedProfitsChargeableFy1(
      CorporationTaxCalculatorParameters(
        fieldValueRetriever.cp295(),
        HmrcAccountingPeriod(fieldValueRetriever.cp1(),fieldValueRetriever.cp2())
      )
    )
}
