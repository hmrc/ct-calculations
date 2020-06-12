/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC402(value: Option[Int]) extends CtBoxIdentifier(name = " Previous Gross turnover from OPW")
with CtOptionalInteger
with Input
  with ValidatableBox[AccountsBoxRetriever] {
  override def validate(boxRetriever: AccountsBoxRetriever): Set[CtValidation] = {
    val ac404 = boxRetriever.ac404()

    collectErrors(
      failIf(ac404.value.isDefined && value.isEmpty){
        Set(CtValidation(Some("AC402"), "error.AC402.required"))
      },
      exceedsMax(value,999999),
      validateZeroOrPositiveInteger(this)
    )
  }
}

object AC402 {
  def apply(value: Int): AC402 = AC402(Some(value))
}