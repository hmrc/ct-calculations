/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x

import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDormancyBoxRetriever
import uk.gov.hmrc.ct.box._

case class ACQ8991(value: Option[Boolean]) extends CtBoxIdentifier(name = "The company has previously traded.")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[Frs10xDormancyBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs10xDormancyBoxRetriever): Set[CtValidation] = {
    failIf(boxRetriever.acq8999().orFalse) {
      validateAsMandatory(this)
    }
  }
}
