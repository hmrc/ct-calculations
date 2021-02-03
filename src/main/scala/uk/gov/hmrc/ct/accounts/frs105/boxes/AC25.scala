/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts.{AC3, AC4}
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.validation.TurnoverValidation

case class AC25(value: Option[Int]) extends CtBoxIdentifier(name = "Income from covid-19 business support grants")
  with CtOptionalInteger
  with Input
  with ValidatableBox[AccountsBoxRetriever] with TurnoverValidation {

  val accountsStart: AccountsBoxRetriever => AC3 = {
    boxRetriever: AccountsBoxRetriever =>
      boxRetriever.ac3()
  }

  val accountEnd: AccountsBoxRetriever => AC4 = {
    boxRetriever: AccountsBoxRetriever =>
      boxRetriever.ac4()
  }

  override def validate(boxRetriever: AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateHmrcTurnover(boxRetriever, accountsStart, accountEnd, secondaryIncome = boxRetriever.ac13.orZero)
    )
  }
}

//  override def validate(boxRetriever: Frs10xAccountsBoxRetriever): Set[CtValidation] = {
//    val ac13 = boxRetriever.ac13()
//    val ac17 = boxRetriever.ac17()
//
//    val doCorrectValidation: Set[CtValidation] =
//      if (value.getOrElse(0) == 0) {
//        validationSuccess
//      } else {
//        (ac13.value, ac17.value) match {
//          case (Some(_), None) =>
//            validateHmrcTurnover(boxRetriever, accountsStart, accountEnd, errorSuffix = ".hmrc.turnover.AC13", secondaryIncome = ac13.orZero)
//          case (None, Some(_)) =>
//            validateHmrcTurnover(boxRetriever, accountsStart, accountEnd, errorSuffix = ".hmrc.turnover.AC17", secondaryIncome = ac17.orZero)
//          case _ => validationSuccess
//        }
//      }
//
//    collectErrors(
//      doCorrectValidation,
//      validateZeroOrPositiveInteger(this),
//        validateHmrcTurnover(boxRetriever, accountsStart, accountEnd, secondaryIncome = boxRetriever.ac13.orZero)
//    )
//  }