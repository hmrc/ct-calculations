/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC322(value: Option[String]) extends CtBoxIdentifier(name = "Tangible fixed assets depreciation policy")
                                      with CtOptionalString
                                      with Input
                                      with ValidatableBox[Frs102AccountsBoxRetriever]
                                      with Validators {


  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateStringMaxLength("AC322", value.getOrElse(""), StandardCohoTextFieldLimit),
      validateCoHoStringReturnIllegalChars("AC322", this)
    )
  }
}
