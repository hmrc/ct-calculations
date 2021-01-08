/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.BalanceSheetCreditorsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever._

case class AC162(value: Option[Int]) extends CtBoxIdentifier(name = "Creditors after one year - Total (CY)")
  with CtOptionalInteger
  with ValidatableBox[FullAccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: FullAccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    failIf (anyHaveValue(ac64(), ac65()))(
      collectErrors(
        validateMoney(value),
        totalEqualToCurrentAmount(boxRetriever)
      )
    )
  }

  def totalEqualToCurrentAmount(boxRetriever: FullAccountsBoxRetriever)() = {
    failIf(this.value != boxRetriever.ac64().value) {
      Set(CtValidation(None, "error.creditorsAfterOneYear.currentYearTotal.notEqualsTo.currentYearAmount"))
    }
  }

}

object AC162 extends Calculated[AC162, FullAccountsBoxRetriever] with BalanceSheetCreditorsCalculator {

  override def calculate(boxRetriever: FullAccountsBoxRetriever): AC162 = {
    import boxRetriever._
    calculateAC162(
      ac156(),
      ac158(),
      ac160()
    )
  }

}
