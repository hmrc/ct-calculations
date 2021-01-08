/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.ct600.v2.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v2.retriever.CT600BoxRetriever

case class B46R(value: Int) extends CtBoxIdentifier("Tax rounded") with CtInteger

object B46R extends CorporationTaxCalculator with Calculated[B46R, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B46R =
    corporationTaxFy1RoundedHalfDown(fieldValueRetriever.b46())

}
