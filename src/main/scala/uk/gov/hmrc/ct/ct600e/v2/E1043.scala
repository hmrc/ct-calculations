/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.ct600.v2.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

case class E1043(value: Option[Int]) extends CtBoxIdentifier("Second Financial Year") with CtOptionalInteger

object E1043 extends CorporationTaxCalculator with Calculated[E1043, CT600EBoxRetriever] {

  override def calculate(fieldValueRetriever: CT600EBoxRetriever): E1043 = {
    E1043(financialYear2(HmrcAccountingPeriod(fieldValueRetriever.e1021, fieldValueRetriever.e1022)))
  }
}
