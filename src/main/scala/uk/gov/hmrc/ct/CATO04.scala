/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.ct600.v2.calculations.MarginalRateReliefCalculator
import uk.gov.hmrc.ct.ct600.v2.retriever.CT600BoxRetriever

case class CATO04(value: BigDecimal) extends CtBoxIdentifier("Marginal Rate Relief") with CtBigDecimal

object CATO04 extends Calculated[CATO04, CT600BoxRetriever] with MarginalRateReliefCalculator {

  override def calculate(boxRetriever: CT600BoxRetriever): CATO04 = {
    computeMarginalRateRelief(b37 = boxRetriever.b37(),
                              b44 = boxRetriever.b44(),
                              b54 = boxRetriever.b54(),
                              b38 = boxRetriever.b38(),
                              b39 = boxRetriever.b39(),
                              accountingPeriod = HmrcAccountingPeriod(boxRetriever.cp1(), boxRetriever.cp2()))
  }
}
