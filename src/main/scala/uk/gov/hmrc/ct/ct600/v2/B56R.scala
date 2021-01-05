/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.ct600.v2.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v2.retriever.CT600BoxRetriever

case class B56R(value: Int) extends CtBoxIdentifier("Tax rounded") with CtInteger

object B56R extends CorporationTaxCalculator with Calculated[B56R, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B56R =
    corporationTaxFy2RoundedHalfUp(fieldValueRetriever.b56())
}
