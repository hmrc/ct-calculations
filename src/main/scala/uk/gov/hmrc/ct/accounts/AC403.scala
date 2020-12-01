/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.offPayRollWorking.DeductionCannotBeGreaterThanProfit

case class AC403(value: Option[Int]) extends CtBoxIdentifier(name = "Current Deductions from OPW")
  with CtOptionalInteger
  with Input
  with Debit
  with ValidatableBox[AccountsBoxRetriever] {
  override def validate(boxRetriever: AccountsBoxRetriever): Set[CtValidation] = {
    val ac401 = boxRetriever.ac401()
    collectErrors(
      failIf(ac401.value.isDefined && value.isEmpty){
        Set(CtValidation(Some("AC403"), "error.AC403.required"))
      },
      DeductionCannotBeGreaterThanProfit(boxRetriever.ac401(), this)
    )
  }
}

object AC403 {
  def apply(value: Int): AC403 = AC403(Some(value))
}
