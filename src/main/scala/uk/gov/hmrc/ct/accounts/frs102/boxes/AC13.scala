/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.AccountsPreviousPeriodValidation
import uk.gov.hmrc.ct.accounts.frs102.retriever.{Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._

case class AC13(value: Option[Int]) extends CtBoxIdentifier(name = "Turnover (previous PoA)")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs102AccountsBoxRetriever]
  with AccountsPreviousPeriodValidation
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    val errors = collectErrors(
      validateInputAllowed("AC13", boxRetriever.ac205()),
      validateZeroOrPositiveInteger(this)
    )

    if(errors.isEmpty) {
      validateMoney(value, 0)
    } else {
      errors
    }
  }
}
