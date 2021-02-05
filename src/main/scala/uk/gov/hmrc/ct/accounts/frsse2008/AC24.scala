/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frsse2008

import uk.gov.hmrc.ct.accounts.AC12
import uk.gov.hmrc.ct.accounts.frs10x.helpers.CovidProfitAndLossValidationHelper
import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, CtValidation, Input}

case class AC24(value: Option[Int]) extends CtBoxIdentifier(name = "Income from covid-19 business support grants")
  with CtOptionalInteger
  with Input
  with CovidProfitAndLossValidationHelper[Frsse2008AccountsBoxRetriever] {

  override val turnover: Frsse2008AccountsBoxRetriever => AC12 =
    boxRetriever => boxRetriever.ac12()

  override val grossProfitOrLoss: Frsse2008AccountsBoxRetriever => AC16 =
    boxRetriever => boxRetriever.ac16()

  override def validate(boxRetriever: Frsse2008BoxRetriever): Set[CtValidation] = {
    collectErrors(
      doCorrectValidation(boxRetriever),
      validateZeroOrPositiveInteger(this),
//      failIf(boxRetriever.hmrcFiling().value && !boxRetriever.abridgedFiling().value)(
//        collectErrors(
//          validateHmrcTurnover(boxRetriever, accountsStart, accountEnd, secondaryIncome = boxRetriever.ac12.orZero)
//        )
      )
  }

override def processValidation(boxRetriever: BoxRetriever): PartialFunction[Box, Set[CtValidation]] = {
  case ac16: AC16 if ac16.hasValue => validateTurnover(boxRetriever, ac16, boxId)
  case ac16: AC16 if !ac16.hasValue => validationSuccess
  case _ => throw new Exception("Unexpected error")

}
}