/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.BalanceSheetDebtorsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever._

case class AC140(value: Option[Int]) extends CtBoxIdentifier(name = "Debtors - Total (CY)")
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
    failIf(this.value != boxRetriever.ac52().value) {
      Set(CtValidation(None, "error.debtors.currentYearTotal.notEqualsTo.currentYearAmount"))
    }
  }
}

object AC140 extends Calculated[AC140, FullAccountsBoxRetriever] with BalanceSheetDebtorsCalculator {

  override def calculate(boxRetriever: FullAccountsBoxRetriever): AC140 = {
    import boxRetriever._
    calculateAC140(
      ac134(),
      ac136(),
      ac138()
    )
  }
}
