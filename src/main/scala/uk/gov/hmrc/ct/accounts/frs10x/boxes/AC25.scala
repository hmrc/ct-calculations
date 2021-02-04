/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AC12, AC3, AC4, AccountsPreviousPeriodValidation}
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.validation.TurnoverValidation

case class AC25(value: Option[Int]) extends CtBoxIdentifier(name = "Income from covid-19 business support grants")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever] with TurnoverValidation {

  val accountsStart: AccountsBoxRetriever => AC3 = {
    boxRetriever: AccountsBoxRetriever =>
      boxRetriever.ac3()
  }

  val accountEnd: AccountsBoxRetriever => AC4 = {
    boxRetriever: AccountsBoxRetriever =>
      boxRetriever.ac4()
  }

  val turnover: Frs10xAccountsBoxRetriever => AC13 = {
    boxRetriever =>
      boxRetriever.ac13()
  }

  val grossProfitOrLoss: Frs10xAccountsBoxRetriever => AC17 = {
    boxRetriever =>
      boxRetriever.ac17()
  }

  override def validate(boxRetriever: Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    val doCorrectValidation: Set[CtValidation] = {
      if (value.getOrElse(0) == 0) {
        validationSuccess
      } else {
        getCorrectBox(boxRetriever) match {
          case box: AC13 => returnCorrectVali(boxRetriever, box, "AC13")
          case box: AC17 => returnCorrectVali(boxRetriever, box, "AC17")
        }
      }
    }

    collectErrors(
      validateZeroOrPositiveInteger(this),
      doCorrectValidation
    )
  }

  private def getCorrectBox(boxRetriever: Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever): Box = {
    val isAbridgedJourney = boxRetriever.abridgedFiling().value

    val correctBoxWithBoxId: CtBoxIdentifier with CtOptionalInteger with Input with ValidatableBox[_ >: Frs10xAccountsBoxRetriever <: AccountsBoxRetriever] with AccountsPreviousPeriodValidation =
      if (isAbridgedJourney) grossProfitOrLoss(boxRetriever)
      else turnover(boxRetriever)

    correctBoxWithBoxId
  }


  private def returnCorrectVali(boxRetriever: Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever, box: Box, boxId: String): Set[CtValidation] = {
    validateHmrcTurnover(boxRetriever, accountsStart, accountEnd, errorSuffix = s".hmrc.turnover.$boxId", secondaryIncome = box.orZero, minimumAmount = Some(0))
  }

  private type Box = CtBoxIdentifier with CtOptionalInteger with Input with ValidatableBox[_ >: Frs10xAccountsBoxRetriever <: AccountsBoxRetriever] with AccountsPreviousPeriodValidation

}