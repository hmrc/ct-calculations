/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.ct600.v2.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

case class E1033(value: Int) extends CtBoxIdentifier("First Financial Year") with CtInteger

object E1033 extends CorporationTaxCalculator with Calculated[E1033, CT600EBoxRetriever] {

  override def calculate(fieldValueRetriever: CT600EBoxRetriever): E1033 = {
    E1033(financialYear1(HmrcAccountingPeriod(fieldValueRetriever.e1021(), fieldValueRetriever.e1022)))
  }
}
