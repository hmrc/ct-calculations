/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalBigDecimal}
import uk.gov.hmrc.ct.ct600.v2.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever


case class A13(value: Option[BigDecimal]) extends CtBoxIdentifier(name = "A13 - Tax payable under S419 ICTA 1988") with CtOptionalBigDecimal

object A13 extends Calculated[A13, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(fieldValueRetriever: CT600ABoxRetriever): A13 = {
    calculateA13(a3 = fieldValueRetriever.a3(),
                 a7 = fieldValueRetriever.a7(),
                 a11 = fieldValueRetriever.a11())
  }
}
