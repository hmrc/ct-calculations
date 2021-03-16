/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

case class B474(value: Option[Int]) extends CtBoxIdentifier(name = "JRB and EOTHO overpayments") with CtOptionalInteger

object B474 extends CorporationTaxCalculator with Calculated[B474, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B474 = {
    calculateJrbEothoOverpayments(fieldValueRetriever.b476(), fieldValueRetriever.b477())
  }
}