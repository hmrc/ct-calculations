/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.AccountsPreviousPeriodValidation
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC13(value: Option[Int]) extends CtBoxIdentifier(name = "Turnover (previous PoA)")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs10xAccountsBoxRetriever]
  with AccountsPreviousPeriodValidation
  with Validators {

  override def validate(boxRetriever: Frs10xAccountsBoxRetriever): Set[CtValidation] = {
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