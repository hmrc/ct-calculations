/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frsse2008

import uk.gov.hmrc.ct.accounts.frs10x.helpers.CovidProfitAndLossValidationHelper
import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, CtValidation, Input}

case class AC25(value: Option[Int]) extends CtBoxIdentifier(name = "Income from covid-19 business support grants")
  with CtOptionalInteger
  with Input
  with CovidProfitAndLossValidationHelper[Frsse2008AccountsBoxRetriever] {


  override val turnover: Frsse2008AccountsBoxRetriever => AC17 =
    boxRetriever => boxRetriever.ac17()

  override val grossProfitOrLoss: Frsse2008AccountsBoxRetriever => Box =
    boxRetriever => boxRetriever.ac13()


  override def validate(boxRetriever: Frsse2008BoxRetriever): Set[CtValidation] = {
    collectErrors(
      doCorrectValidation(boxRetriever),
      validateZeroOrPositiveInteger(this)
    )
  }

  override def processValidation(boxRetriever: Frsse2008BoxRetriever): PartialFunction[Box, Set[CtValidation]] = {
    case ac17: AC17 if ac17.hasValue => {
      validateHmrcTurnover(boxRetriever, accountsStart, accountEnd, errorSuffix = ".hmrc.turnover.AC17", secondaryIncome = ac17.orZero)
    }
    case ac17: AC17 if !ac17.hasValue => validationSuccess
  }

}