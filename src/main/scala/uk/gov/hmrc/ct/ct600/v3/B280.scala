/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoolean, CtBoxIdentifier}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator

case class B280(value: Boolean) extends CtBoxIdentifier("Amounts carried back from later periods") with CtBoolean

//Hidden field, used for XML only
object B280 extends CorporationTaxCalculator with Calculated[B280, ComputationsBoxRetriever] {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): B280 =
    areAmountsCarriedBackFromLaterPeriods(fieldValueRetriever.cp286())
}
