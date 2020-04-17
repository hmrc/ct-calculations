/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.AccountsPreviousPeriodValidation
import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC15(value: Option[Int]) extends CtBoxIdentifier(name = "Cost of sales (current PoA)")
  with CtOptionalInteger
  with Input
  with ValidatableBox[FullAccountsBoxRetriever]
  with Validators
  with AccountsPreviousPeriodValidation
  with Debit {

  override def validate(boxRetriever: FullAccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateInputAllowed("AC15", boxRetriever.ac205()),
      validateMoney(value, min = 0)
    )
  }
}
