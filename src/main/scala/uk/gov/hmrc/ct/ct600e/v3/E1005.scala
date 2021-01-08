/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box.{AnnualConstant, Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

case class E1005(value: BigDecimal) extends CtBoxIdentifier(name = "First Financial Year Rate Of Tax") with AnnualConstant with CtBigDecimal

object E1005 extends CorporationTaxCalculator with Calculated[E1005, CT600EBoxRetriever] {

  override def calculate(fieldValueRetriever: CT600EBoxRetriever): E1005 =
    E1005(rateOfTaxFy1(fieldValueRetriever.e3()))
}
