/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts.{AC3, AC4}
import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever}
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, CtValidation, Input, ValidatableBox}
import uk.gov.hmrc.ct.validation.TurnoverValidation

case class AC24(value: Option[Int]) extends CtBoxIdentifier(name = "Income from covid-19 business support grants")
  with CtOptionalInteger
  with Input
  with ValidatableBox[AccountsBoxRetriever with FilingAttributesBoxValueRetriever]
  with TurnoverValidation {

  val accountsStart: AccountsBoxRetriever => AC3 = {
    boxRetriever: AccountsBoxRetriever =>
      boxRetriever.ac3()
  }

  val accountEnd: AccountsBoxRetriever => AC4 = {
    boxRetriever: AccountsBoxRetriever =>
      boxRetriever.ac4()
  }

  override def validate(boxRetriever: AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    collectErrors(
      failIf(boxRetriever.hmrcFiling().value && !boxRetriever.abridgedFiling().value)(
        collectErrors(
          validateHmrcTurnover(boxRetriever, accountsStart, accountEnd, secondaryIncome = boxRetriever.ac12().orZero)
        )
      )
    )
  }
}
//}override def validate(boxRetriever: AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
//    val incomeFromCovidGrants: Int = value.getOrElse(0)
//    val ac12 = boxRetriever.ac12()
//      val ac16 = boxRetriever.ac16()
//
//    val doCorrectValidation: Set[CtValidation] = {
//      if (value.getOrElse(0) == 0) {
//        validationSuccess
//      } else {
//        (ac12.value, ac16.value) match {
//          case (Some(_), None) => {
//            validateHmrcTurnover(boxRetriever, accountsStart, accountEnd, errorSuffix = ".hmrc.turnover.AC12", secondaryIncome = ac12.orZero)
//          }
//          case (None, Some(_)) => {
//            validateHmrcTurnover(boxRetriever, accountsStart, accountEnd, errorSuffix = ".hmrc.turnover.AC16", secondaryIncome = ac16.orZero)
//          }
//          case _ => validationSuccess
//        }
//      }
//    }
//
//
//    collectErrors(
//      doCorrectValidation,
//      validateZeroOrPositiveInteger(this),
//      failIf(boxRetriever.hmrcFiling().value && !boxRetriever.abridgedFiling().value)(
//        collectErrors(
//          validateHmrcTurnover(boxRetriever, accountsStart, accountEnd, secondaryIncome = boxRetriever.ac12.orZero)
//        )
//      )
//    )
//  }
//}

//collectErrors(
//incomeFromCovidGrants match {
//  case 0 => validationSuccess
//  case _ => {
//  validateHmrcTurnover(boxRetriever, accountsStart, accountEnd, secondaryIncome = boxRetriever.ac12.orZero)
//}