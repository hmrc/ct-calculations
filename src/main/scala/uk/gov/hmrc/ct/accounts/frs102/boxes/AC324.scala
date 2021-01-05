/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC324(value: Option[String]) extends CtBoxIdentifier(name = "Valuation information and policy ")
                                      with CtOptionalString
                                      with Input
                                      with ValidatableBox[Frs102AccountsBoxRetriever]
                                      with Validators {


  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateStringMaxLength("AC324", value.getOrElse(""), StandardCohoTextFieldLimit),
      validateCoHoStringReturnIllegalChars("AC324", this)
    )
  }
}
