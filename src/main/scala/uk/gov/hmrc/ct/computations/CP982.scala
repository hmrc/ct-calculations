/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP982(value: Option[Int]) extends CtBoxIdentifier(name = "Expenses from Off-payroll working (IR35)") with CtOptionalInteger with Input with ValidatableBox[ComputationsBoxRetriever] {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
    validateZeroOrPositiveInteger(this),
      cp981Breakdown(this, boxRetriever)
    )

  }


  def cp981Breakdown(value: CtOptionalInteger, boxRetriever: ComputationsBoxRetriever): Set[CtValidation]= {
    val cp981Error = (boxRetriever.cp983().orZero - boxRetriever.cp981().orZero) - boxRetriever.cp980().orZero
    failIf(value.isPositive && value.orZero > cp981Error) {
      val cp981Positive = if(cp981Error < 0) 0 else cp981Error
      Set(CtValidation(Some("CP982"), "error.cp982.breakdown", Some(Seq(cp981Positive.toString))))
    }
  }
}

object CP982 {

  def apply(value: Int): CP982 = CP982(Some(value))
}
