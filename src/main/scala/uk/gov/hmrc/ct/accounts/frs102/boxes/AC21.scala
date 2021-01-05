/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.AccountsPreviousPeriodValidation
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC21(value: Option[Int]) extends CtBoxIdentifier(name = "Administrative expenses (previous PoA)")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs102AccountsBoxRetriever]
  with Validators
  with AccountsPreviousPeriodValidation
  with Debit {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] =
    collectErrors(
      validateInputAllowed("AC21", boxRetriever.ac205()),
      validateMoney(value)
    )
}
