/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever

case class LPQ04(value: Option[Boolean]) extends CtBoxIdentifier(name = "Is the company controlled by 5 or fewer participators none of whom are directors?") with CtOptionalBoolean with Input with ValidatableBox[CT600ABoxRetriever] {
  override def validate(boxRetriever: CT600ABoxRetriever): Set[CtValidation] = validateBooleanAsMandatory("LPQ04", this)
}
