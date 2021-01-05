/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC7100(value: Option[Boolean]) extends CtBoxIdentifier(name = "Enter Accounting policies note?")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[Frs10xDirectorsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs10xDirectorsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      failIf(value != Some(true))(Set(CtValidation(Some("AC7100"), "error.AC7100.required.true")))
    )
  }
}
