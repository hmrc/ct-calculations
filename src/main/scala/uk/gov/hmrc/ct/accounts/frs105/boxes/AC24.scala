/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, CtValidation, Input, ValidatableBox}
import uk.gov.hmrc.ct.validation.TurnoverValidation

case class AC24(value: Option[Int]) extends CtBoxIdentifier(name = "Income from covid-19 business support grants")
  with CtOptionalInteger
  with Input
  with ValidatableBox[AccountsBoxRetriever with FilingAttributesBoxValueRetriever]
  with TurnoverValidation {

  val accountsStart = {
    boxRetriever: AccountsBoxRetriever =>
      boxRetriever.ac3()
  }

  val accountEnd = {
    boxRetriever: AccountsBoxRetriever =>
      boxRetriever.ac4()
  }

  override def validate(boxRetriever: AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    collectErrors(
      failIf(boxRetriever.hmrcFiling().value && !boxRetriever.abridgedFiling().value)(
        collectErrors(
          validateHmrcTurnover(boxRetriever, accountsStart, accountEnd, secondaryIncome = boxRetriever.ac12.orZero,minimumAmount=Some(0))
        )
      )
    )
  }
}
