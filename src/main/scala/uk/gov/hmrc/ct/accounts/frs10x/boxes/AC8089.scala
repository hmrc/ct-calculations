/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDormancyBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC8089(value: Option[Boolean]) extends CtBoxIdentifier(name = "The directors acknowledge the company was entitled to exemption under section 480 of the Companies Act 2006 relating to dormant companies (dormant companied only).")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[Frs10xDormancyBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs10xDormancyBoxRetriever): Set[CtValidation] = {
    failIf(boxRetriever.acq8999().orFalse) (
      validateBooleanAsTrue("AC8089", this)
    )
  }
}
