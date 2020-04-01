/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP980(value: Option[Int]) extends CtBoxIdentifier(name = "Remuneration from Off-payroll working (IR35)")
  with CtOptionalInteger
  with Input
  with ValidatableBox[ComputationsBoxRetriever with AccountsBoxRetriever] {
  override def validate(boxRetriever: ComputationsBoxRetriever with AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateZeroOrPositiveInteger(this),
      exceedsMax(this.value, 999999),
      cp980Breakdown(this, boxRetriever)

    )

  }

  def cp980Breakdown(value: CtOptionalInteger, boxRetriever: ComputationsBoxRetriever with AccountsBoxRetriever): Set[CtValidation] = {
    failIf(value.orZero > (boxRetriever.ac401().orZero - boxRetriever.ac403().orZero)) {
      Set(CtValidation(Some("CP980"), "error.cp980.breakdown"))
    }
  }

}

object CP980 {

  def apply(int: Int): CP980 = CP980(Some(int))


}

