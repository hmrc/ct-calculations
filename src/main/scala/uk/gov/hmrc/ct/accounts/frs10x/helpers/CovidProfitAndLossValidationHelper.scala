/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.helpers

import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AC3, AC4}
import uk.gov.hmrc.ct.box.ValidatableBox.OptionalIntIdBox
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, CtValidation, ValidatableBox}
import uk.gov.hmrc.ct.validation.TurnoverValidation

trait CovidProfitAndLossValidationHelper[T <: AccountsBoxRetriever] extends ValidatableBox[T with FilingAttributesBoxValueRetriever] with TurnoverValidation {
  self: OptionalIntIdBox =>

  val value: Option[Int]

  val ac12Id: String = "AC12"
  val ac13Id: String = "AC13"
  val ac16Id: String = "AC16"
  val ac17Id: String = "AC17"

  val accountsStart: AccountsBoxRetriever => AC3 = {
    boxRetriever: AccountsBoxRetriever =>
      boxRetriever.ac3()
  }

  val accountEnd: AccountsBoxRetriever => AC4 = {
    boxRetriever: AccountsBoxRetriever =>
      boxRetriever.ac4()
  }

  val turnover: T => Box

  val grossProfitOrLoss: T => Box

  def processValidation(boxRetriever: BoxRetriever): PartialFunction[Box, Set[CtValidation]]

  def doCorrectValidation(boxRetriever: BoxRetriever): Set[CtValidation]  = {
    if (value.getOrElse(0) <= 0) {
      validationSuccess
    } else {
      processValidation(boxRetriever)(getCorrectBox(boxRetriever))
  }
  }

  def getCorrectBox(boxRetriever: BoxRetriever): Box = {
     val isAbridgedJourney = boxRetriever.abridgedFiling().value
// do we need full accounts in here?
    val correctBoxWithBoxId =
      if (isAbridgedJourney) grossProfitOrLoss(boxRetriever)
      else turnover(boxRetriever)

    correctBoxWithBoxId
  }


   def validateTurnover(boxRetriever: BoxRetriever, box: Box, boxId: String): Set[CtValidation] = {
    validateHmrcTurnover(boxRetriever, accountsStart, accountEnd, errorSuffix = s".hmrc.turnover.$boxId", secondaryIncome = box.orZero, minimumAmount = Some(0))
  }

  type BoxRetriever = T with FilingAttributesBoxValueRetriever

  type Frs10xBoxRetriever = Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever

  type Frsse2008BoxRetriever = Frsse2008AccountsBoxRetriever with FilingAttributesBoxValueRetriever

  type Box = CtBoxIdentifier with CtOptionalInteger
}
