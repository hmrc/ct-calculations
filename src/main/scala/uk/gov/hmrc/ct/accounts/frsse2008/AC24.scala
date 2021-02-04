/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frsse2008

import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AC3, AC4}
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, CtValidation, Input, ValidatableBox}
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.validation.TurnoverValidation

case class AC24(value: Option[Int]) extends CtBoxIdentifier(name = "Income from covid-19 business support grants")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frsse2008AccountsBoxRetriever with FilingAttributesBoxValueRetriever]
  with TurnoverValidation {

  val accountsStart: AccountsBoxRetriever => AC3 = {
    boxRetriever: AccountsBoxRetriever =>
      boxRetriever.ac3()
  }

  val accountEnd: AccountsBoxRetriever => AC4 = {
    boxRetriever: AccountsBoxRetriever =>
      boxRetriever.ac4()
  }

  override def validate(boxRetriever: Frsse2008AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    val ac16 = boxRetriever.ac16()

    val doCorrectValidation: Set[CtValidation] = {
      if (value.getOrElse(0) == 0) {
        validationSuccess
      } else {
        ac16.value match {
          case Some(_) =>
            validateHmrcTurnover(boxRetriever, accountsStart, accountEnd, errorSuffix = ".hmrc.turnover.AC16", secondaryIncome = ac16.orZero)
          case None => validationSuccess


          case _ => validationSuccess
        }
      }
    }

    collectErrors(
      doCorrectValidation,
      validateZeroOrPositiveInteger(this),
      failIf(boxRetriever.hmrcFiling().value && !boxRetriever.abridgedFiling().value)(
        collectErrors(
          validateHmrcTurnover(boxRetriever, accountsStart, accountEnd, secondaryIncome = boxRetriever.ac12.orZero)
        )
      )
    )
  }
}