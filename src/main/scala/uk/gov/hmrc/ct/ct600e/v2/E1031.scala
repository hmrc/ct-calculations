/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever
import uk.gov.hmrc.ct.ct600e.validations.ValidateDeclarationNameOrStatus

case class E1031(value: Option[String]) extends CtBoxIdentifier("Claimer's status") with CtOptionalString with Input
  with ValidatableBox[CT600EBoxRetriever] with ValidateDeclarationNameOrStatus[CT600EBoxRetriever] {
  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = validateDeclarationNameOrStatus("E1031", this)
}
