/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.offPayRollWorking.DeductionCannotBeGreaterThanProfit
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP981(value: Option[Int]) extends CtBoxIdentifier(name = "Deductions from Off-payroll working (IR35)") with CtOptionalInteger with Input with ValidatableBox[ComputationsBoxRetriever] {
  override def validate(boxRetriever: ComputationsBoxRetriever ): Set[CtValidation] = {
    collectErrors(
      validateZeroOrPositiveInteger(this),
      DeductionCannotBeGreaterThanProfit(boxRetriever.cp983(), this)
    )
  }
}

object CP981 {
  def apply(value: Int): CP981 = CP981(Some(value))
}
