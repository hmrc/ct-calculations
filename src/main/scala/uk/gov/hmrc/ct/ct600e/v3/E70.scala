/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

case class E70(value: Option[Int]) extends CtBoxIdentifier("Income From other charities – excluding any amounts included on form CT600")
  with CtOptionalInteger with Input with ValidatableBox[CT600EBoxRetriever] {

  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = validateZeroOrPositiveInteger(this)
}
