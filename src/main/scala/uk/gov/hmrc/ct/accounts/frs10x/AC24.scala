/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x

import uk.gov.hmrc.ct.accounts.AC12
import uk.gov.hmrc.ct.accounts.frs10x.helpers.CovidProfitAndLossValidationHelper
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC24(value: Option[Int]) extends CtBoxIdentifier(name = "Income from covid-19 business support grants")
  with CtOptionalInteger
  with Input
  with CovidProfitAndLossValidationHelper[Frs10xAccountsBoxRetriever] {

  val ac12Id = "AC12"
  val ac16Id = "AC16"

  val turnover: Frs10xAccountsBoxRetriever => AC12 = {
    boxRetriever =>
      boxRetriever.ac12()
  }

  val grossProfitOrLoss: Frs10xAccountsBoxRetriever => AC16 = {
    boxRetriever =>
      boxRetriever.ac16()
  }

  override def processValidation(boxRetriever: Frs10xBoxRetriever): PartialFunction[Box, Set[CtValidation]] = {
    case box: AC12 => validateTurnover(boxRetriever, box, ac12Id)
    case box: AC16 => validateTurnover(boxRetriever, box, ac16Id)
  }

  override def validate(boxRetriever: Frs10xBoxRetriever): Set[CtValidation] = {
    collectErrors(
          validateZeroOrPositiveInteger(this),
          doCorrectValidation(boxRetriever)
      )
  }
}
