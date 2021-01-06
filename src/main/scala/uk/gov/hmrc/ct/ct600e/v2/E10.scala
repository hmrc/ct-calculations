/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

case class E10(value: Option[Int]) extends CtBoxIdentifier("Income from other charities") with CtOptionalInteger with Input with ValidatableBox[CT600EBoxRetriever] {
  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = {
    validateZeroOrPositiveInteger(this)
  }
}
