/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600DeclarationBoxRetriever
import uk.gov.hmrc.ct.ct600e.validations.ValidateDeclarationNameOrStatus

case class B985(value: Option[String]) extends CtBoxIdentifier("Declaration status") with CtOptionalString with Input
  with ValidatableBox[CT600DeclarationBoxRetriever] with ValidateDeclarationNameOrStatus[CT600DeclarationBoxRetriever] {
  override def validate(boxRetriever: CT600DeclarationBoxRetriever): Set[CtValidation] = validateDeclarationNameOrStatus("B985", this)
}
