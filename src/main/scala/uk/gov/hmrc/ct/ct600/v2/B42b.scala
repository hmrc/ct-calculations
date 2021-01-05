/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v2.retriever.CT600BoxRetriever

case class B42b(value: Option[Boolean]) extends CtBoxIdentifier("Marginal Rate Relief Claimed") with CtOptionalBoolean with Input with ValidatableBox[CT600BoxRetriever] {
  override def validate(boxRetriever: CT600BoxRetriever): Set[CtValidation] = validateAsMandatory(this)
}
