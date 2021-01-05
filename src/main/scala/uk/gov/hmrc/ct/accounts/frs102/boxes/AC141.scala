/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.BalanceSheetDebtorsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever._

case class AC141(value: Option[Int]) extends CtBoxIdentifier(name = "Debtors - Total (PY)")
  with CtOptionalInteger
  with ValidatableBox[FullAccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: FullAccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    failIf (anyHaveValue(ac52(), ac53()))(
      collectErrors(
        validateMoney(value),
        totalEqualToCurrentAmount(boxRetriever)
      )
    )
  }

  def totalEqualToCurrentAmount(boxRetriever: FullAccountsBoxRetriever)() = {
    failIf(this.value != boxRetriever.ac53().value) {
      Set(CtValidation(None, "error.debtors.previousYearTotal.notEqualsTo.previousYearAmount"))
    }
  }

}

object AC141 extends Calculated[AC141, FullAccountsBoxRetriever] with BalanceSheetDebtorsCalculator {

  override def calculate(boxRetriever: FullAccountsBoxRetriever): AC141 = {
    import boxRetriever._
    calculateAC141(
      ac135(),
      ac137(),
      ac139()
    )
  }

}
