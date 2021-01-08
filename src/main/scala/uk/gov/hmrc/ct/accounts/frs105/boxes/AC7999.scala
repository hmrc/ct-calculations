/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs105.validation.OffBalanceSheetArrangementsValidator
import uk.gov.hmrc.ct.box.ValidatableBox.{StandardCohoTextFieldLimit, ValidNonForeignMoreRestrictiveCharacters}
import uk.gov.hmrc.ct.box._

case class AC7999(value: Option[String])
    extends CtBoxIdentifier(name = "Off balance sheet disclosure footnote")
    with CtOptionalString
    with Input
    with SelfValidatableBox[Frs105AccountsBoxRetriever, Option[String]]
    with OffBalanceSheetArrangementsValidator
{
  override def validate(
      boxRetriever: Frs105AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateAgainstAC7999a(boxRetriever, this.boxId, value),
      validateOptionalStringByLength(1, StandardCohoTextFieldLimit)
    )
  }
}
