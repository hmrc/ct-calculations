/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frsse2008

import uk.gov.hmrc.ct.accounts.frs10x.helpers.CovidProfitAndLossValidationHelper
import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, CtValidation, Input}

case class AC25(value: Option[Int]) extends CtBoxIdentifier(name = "Income from covid-19 business support grants")
  with CtOptionalInteger
  with Input
  with CovidProfitAndLossValidationHelper[Frsse2008AccountsBoxRetriever] {


  override val turnover: Frsse2008AccountsBoxRetriever => Box =
    boxRetriever => boxRetriever.ac13()

  override val grossProfitOrLoss: Frsse2008AccountsBoxRetriever => Box =
    boxRetriever => boxRetriever.ac13()

  override def processValidation(boxRetriever: Frs2008BoxRetriever): PartialFunction[Box, Set[CtValidation]] = {
    case ac13 => Set()
  }

  override def validate(boxRetriever: Frsse2008AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    val ac17 = boxRetriever.ac17()
    val doCorrectValidation: Set[CtValidation] = {
      if (value.getOrElse(0) == 0) {
        validationSuccess
      } else {
        ac17.value match {
          case Some(_) =>
        validateHmrcTurnover(boxRetriever, accountsStart, accountEnd, errorSuffix = ".hmrc.turnover.AC17", secondaryIncome = ac17.orZero)
          case None => validationSuccess
        }
      }
    }

    collectErrors(
      doCorrectValidation,
      validateZeroOrPositiveInteger(this)
    )
  }
}