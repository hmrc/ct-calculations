/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

case class E45(value: Option[Boolean]) extends CtBoxIdentifier("during the period covered by these supplementary pages have you over claimed tax?") with CtOptionalBoolean with Input with ValidatableBox[CT600EBoxRetriever] {
  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = validateBooleanAsMandatory("E45", this)
}
