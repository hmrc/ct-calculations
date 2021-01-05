/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC7901(value: Option[String]) extends CtBoxIdentifier(name = "Post balance sheet events") with CtOptionalString
with Input
with SelfValidatableBox[Frs102AccountsBoxRetriever, Option[String]]
with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors (
      cannotExistErrorIf(!boxRetriever.ac7900().orFalse && value.nonEmpty),

      failIf (boxRetriever.ac7900().orFalse) (
        collectErrors (
          validateStringAsMandatory(),
          validateOptionalStringByLength(1, StandardCohoTextFieldLimit),
          validateCoHoStringReturnIllegalChars()
        )
      )
    )
  }
}
