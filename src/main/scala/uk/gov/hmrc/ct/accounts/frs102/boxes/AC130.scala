/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.BalanceSheetTangibleAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._

case class AC130(value: Option[Int]) extends CtBoxIdentifier(name = "Depreciation of tangible assets that were disposed of during this period")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs102AccountsBoxRetriever]
  with Validators
  with Debit {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateMoney(value, min = 0)
    )
  }
}

object AC130 extends Calculated[AC130, FullAccountsBoxRetriever] with BalanceSheetTangibleAssetsCalculator {

  override def calculate(boxRetriever: FullAccountsBoxRetriever): AC130 = {
    import boxRetriever._
    calculateAC130(
      ac130A(),
      ac130B(),
      ac130C(),
      ac130D(),
      ac130E()
    )
  }
}
