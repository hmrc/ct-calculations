/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalBigDecimal}
import uk.gov.hmrc.ct.ct600.v2.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever

case class A3(value: Option[BigDecimal]) extends CtBoxIdentifier(name = "A3 - Tax chargeable on loans - (Tax due before any relief for loans repaid, released, or written off after the end of the period)")
 with CtOptionalBigDecimal

object A3 extends Calculated[A3, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(fieldValueRetriever: CT600ABoxRetriever): A3 = {
    calculateA3(fieldValueRetriever.a2())
  }
}
