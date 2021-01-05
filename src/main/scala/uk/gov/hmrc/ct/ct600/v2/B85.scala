/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.v2.calculations.Ct600FinalisationCalculator
import uk.gov.hmrc.ct.ct600.v2.retriever.CT600BoxRetriever

case class B85(value: BigDecimal) extends CtBoxIdentifier("Income Tax Repayable to the company") with CtBigDecimal

object B85 extends Ct600FinalisationCalculator with Calculated[B85, CT600BoxRetriever]{

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B85 =
    computeTaxRepayable(
        fieldValueRetriever.b70(),
        fieldValueRetriever.b79(),
        fieldValueRetriever.b84())
}
