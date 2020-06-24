/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.GrossProfitAndLossCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._

case class AC16(value: Option[Int]) extends CtBoxIdentifier(name = "Gross profit or loss (current PoA)")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs102AccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors {
      validateMoney(value)
    }
  }
}

object AC16 extends Calculated[AC16, FullAccountsBoxRetriever] with GrossProfitAndLossCalculator {
  override def calculate(boxRetriever: FullAccountsBoxRetriever): AC16 = {
    calculateAC16(boxRetriever.ac12, boxRetriever.ac401, boxRetriever.ac403, boxRetriever.ac14())
  }
}