/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC5076C(value: Option[String]) extends CtBoxIdentifier(name = "Additional information (optional)")
                                          with CtOptionalString
                                          with Input
                                          with ValidatableBox[Frs102AccountsBoxRetriever] {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors (
      validateStringMaxLength("AC5076C", value.getOrElse(""), StandardCohoTextFieldLimit),
      validateCoHoStringReturnIllegalChars("AC5076C", this)
    )
  }

}
