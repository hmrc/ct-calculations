/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.AccountsPreviousPeriodValidation
import uk.gov.hmrc.ct.accounts.frs102.calculations.GrossProfitAndLossCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._

case class AC17(value: Option[Int]) extends CtBoxIdentifier(name = "Gross profit or loss (previous PoA)")
  with CtOptionalInteger
  with Input
  with SelfValidatableBox[Frs102AccountsBoxRetriever, Option[Int]]
  with AccountsPreviousPeriodValidation
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors (
      validateInputAllowed("AC17", boxRetriever.ac205()),
      validateMoney(value, 0, 632000)
    )
  }
}

object AC17 extends Calculated[AC17, FullAccountsBoxRetriever] with GrossProfitAndLossCalculator {
  override def calculate(boxRetriever: FullAccountsBoxRetriever): AC17 = {
    calculateAC17(boxRetriever.ac13(), boxRetriever.ac25, boxRetriever.ac402, boxRetriever.ac404, boxRetriever.ac15())
  }
}