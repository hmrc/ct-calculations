/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC7401(value: Option[String]) extends CtBoxIdentifier(name = "Financial commitments") with CtOptionalString
with Input
with ValidatableBox[Frs102AccountsBoxRetriever]
with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors (
      cannotExistErrorIf(!boxRetriever.ac7400().orFalse && value.nonEmpty),

      failIf (boxRetriever.ac7400().orFalse) (
        collectErrors (
          validateStringAsMandatory("AC7401", this),
          validateOptionalStringByLength("AC7401", this, 1, StandardCohoTextFieldLimit),
          validateCoHoStringReturnIllegalChars("AC7401", this)
        )
      )
    )
  }
}
