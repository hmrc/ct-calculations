/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.v2.calculations.CorporationTaxAlreadyPaidCalculator
import uk.gov.hmrc.ct.ct600.v2.retriever.CT600BoxRetriever

case class B93(value: BigDecimal) extends CtBoxIdentifier("Corporation Tax Overpaid") with CtBigDecimal

object B93 extends CorporationTaxAlreadyPaidCalculator with Calculated[B93, CT600BoxRetriever]{

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B93 =
    corporationTaxOverpaid(fieldValueRetriever.b86(),
                           fieldValueRetriever.b91())
}
