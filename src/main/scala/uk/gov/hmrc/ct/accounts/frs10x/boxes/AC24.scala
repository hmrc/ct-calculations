/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AC12, AC3, AC4}
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.validation.TurnoverValidation

case class AC24(value: Option[Int]) extends CtBoxIdentifier(name = "Income from covid-19 business support grants")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever]
  with TurnoverValidation {

  val accountsStart: AccountsBoxRetriever => AC3 = {
    boxRetriever: AccountsBoxRetriever =>
      boxRetriever.ac3()
  }

  val accountEnd: AccountsBoxRetriever => AC4 = {
    boxRetriever: AccountsBoxRetriever =>
      boxRetriever.ac4()
  }

  val turnover: Frs10xAccountsBoxRetriever => AC12 = {
    boxRetriever =>
      boxRetriever.ac12()
  }

  val grossProfitOrLoss: Frs10xAccountsBoxRetriever => AC16 = {
    boxRetriever =>
      boxRetriever.ac16()
  }


  override def validate(boxRetriever: Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    val doCorrectValidation: Set[CtValidation] = {
      if (value.getOrElse(0) == 0) {
        validationSuccess
      } else {
        getCorrectBox(boxRetriever) match {
          case box: AC12 => returnCorrectVali(boxRetriever, box, "AC12")
          case box: AC16 => returnCorrectVali(boxRetriever, box, "AC16")
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

    val correctBoxWithBoxId =
      if (isAbridgedJourney) grossProfitOrLoss(boxRetriever)
      else turnover(boxRetriever)

    correctBoxWithBoxId
    }


  private def returnCorrectVali(boxRetriever: Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever, box: Box, boxId: String): Set[CtValidation] = {
    validateHmrcTurnover(boxRetriever, accountsStart, accountEnd, errorSuffix = s".hmrc.turnover.$boxId", secondaryIncome = box.orZero, minimumAmount = Some(0))
  }

  private type Box =
    CtBoxIdentifier with
    CtOptionalInteger with
    Input with
      ValidatableBox[_ >: Frs10xAccountsBoxRetriever with
        FilingAttributesBoxValueRetriever <: AccountsBoxRetriever with
        FilingAttributesBoxValueRetriever] with TurnoverValidation

}
