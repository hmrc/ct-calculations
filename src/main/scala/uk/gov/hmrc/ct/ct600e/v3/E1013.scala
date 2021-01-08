/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever


case class E1013(value: Option[Int]) extends CtBoxIdentifier("Second Financial Year") with CtOptionalInteger

object E1013 extends CorporationTaxCalculator with Calculated[E1013, CT600EBoxRetriever] {

  override def calculate(fieldValueRetriever: CT600EBoxRetriever): E1013 = {
    E1013(financialYear2(HmrcAccountingPeriod(fieldValueRetriever.e3(), fieldValueRetriever.e4)))
  }
}
