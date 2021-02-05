/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.frs10x.helpers.CovidProfitAndLossValidationHelper
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC25(value: Option[Int]) extends CtBoxIdentifier(name = "Income from covid-19 business support grants")
  with CtOptionalInteger
  with Input
  with CovidProfitAndLossValidationHelper[Frs10xAccountsBoxRetriever] {

  override val turnover: Frs10xAccountsBoxRetriever => AC13 = {
    boxRetriever =>
      boxRetriever.ac13()
  }

  override val grossProfitOrLoss: Frs10xAccountsBoxRetriever => AC17 = {
    boxRetriever =>
      boxRetriever.ac17()
  }

  override def validate(boxRetriever: Frs10xBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateZeroOrPositiveInteger(this),
      doCorrectValidation(boxRetriever)
    )
  }

  override def processValidation(boxRetriever: Frs10xBoxRetriever): PartialFunction[Box, Set[CtValidation]] = {
      case box: AC13 => validateTurnover(boxRetriever, box, "AC13")
      case box: AC17 => validateTurnover(boxRetriever, box, "AC17")
    }
}