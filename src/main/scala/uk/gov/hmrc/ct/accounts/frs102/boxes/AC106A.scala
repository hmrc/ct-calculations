/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC106A(value: Option[String]) extends CtBoxIdentifier(name = "Employees note additional information")
  with CtOptionalString
  with Input
  with SelfValidatableBox[Frs102AccountsBoxRetriever, Option[String]]
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors (
      validateOptionalStringByLength(1, StandardCohoTextFieldLimit),
      validateCoHoStringReturnIllegalChars()
    )
  }
}
