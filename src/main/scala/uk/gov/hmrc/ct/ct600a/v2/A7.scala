/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalBigDecimal}
import uk.gov.hmrc.ct.ct600.v2.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever

case class A7(value: Option[BigDecimal]) extends CtBoxIdentifier(name = "A7 Relief due for loans repaid, released or written off (after the end of the period but earlier than nine months and one day after the end of the period)")
 with CtOptionalBigDecimal

object A7 extends Calculated[A7, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(fieldValueRetriever: CT600ABoxRetriever): A7 = {
    calculateA7(fieldValueRetriever.a6())
  }
}
