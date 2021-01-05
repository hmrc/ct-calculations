/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC7110A(value: Option[String]) extends CtBoxIdentifier(name = "Other accounting policies")
                                          with CtOptionalString
                                          with Input
                                          with ValidatableBox[Frs102AccountsBoxRetriever]
                                          with Validators {


  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateStringMaxLength("AC7110A", value.getOrElse(""), StandardCohoTextFieldLimit),
      validateCoHoStringReturnIllegalChars("AC7110A", this)
    )
  }
}
