/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.AccountsPreviousPeriodValidation
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box._


case class AC5052C(value: Option[Int]) extends CtBoxIdentifier(name = "Debtors due after more than one year") with CtOptionalInteger
                                                                                                              with Input
                                                                                                              with ValidatableBox[Frs102AccountsBoxRetriever]
                                                                                                              with AccountsPreviousPeriodValidation
                                                                                                              with SelfValidatableBox[Frs102AccountsBoxRetriever, Option[Int]]
                                                                                                              with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors (
      validateInputAllowed("AC5052C", boxRetriever.ac205()),
      validateMoney(value, min = 0),
      validateOptionalIntegerLessOrEqualBox(boxRetriever.ac53())
    )
  }
}
