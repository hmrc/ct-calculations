/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

case class E190(value: Option[Int]) extends CtBoxIdentifier("Number of subsidiary or associated companies the charity controls at the end of the period. Exclude companies that were dormant throughout the period") with CtOptionalInteger with Input with ValidatableBox[CT600EBoxRetriever]{

  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = validateIntegerRange("E190", this, 0, 999)

}
