/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts.AccountsPreviousPeriodValidation
import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC13(value: Option[Int]) extends CtBoxIdentifier(name = "Turnover (previous PoA)")
  with CtOptionalInteger
  with Input
  with ValidatableBox[AccountsBoxRetriever]
  with AccountsPreviousPeriodValidation
  with Validators {

  override def validate(boxRetriever: AccountsBoxRetriever): Set[CtValidation] = {
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
