/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

case class B150(value: Option[Boolean]) extends CtBoxIdentifier("Banks, building societies, insurance companies and other financial concerns") with CtOptionalBoolean with Input with ValidatableBox[CT600BoxRetriever] {
  override def validate(boxRetriever: CT600BoxRetriever): Set[CtValidation] = validateBooleanAsMandatory("B150", this)
}
