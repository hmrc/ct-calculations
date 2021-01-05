/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.IntangibleAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._

case class AC117(value: Option[Int]) extends CtBoxIdentifier(name = "Cost at [POA END]")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs102AccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateMoney(value, min = 0)
    )
  }
}


object AC117 extends Calculated[AC117, Frs102AccountsBoxRetriever]
  with IntangibleAssetsCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC117 = {
    boxRetriever match {
      case x: AbridgedAccountsBoxRetriever =>
        import boxRetriever._
        calculateAbridgedAC117(x.ac114(), x.ac115(), ac116(), ac209(), ac210())

      case x: FullAccountsBoxRetriever => calculateFullAC117(x.ac117A(), x.ac117B())
    }
  }

}
