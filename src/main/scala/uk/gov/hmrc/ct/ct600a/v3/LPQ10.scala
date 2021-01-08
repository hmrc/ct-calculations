/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever

case class LPQ10(value: Option[Boolean]) extends CtBoxIdentifier(name = "Any other loans?") with CtOptionalBoolean with Input with ValidatableBox[CT600ABoxRetriever] {

  override def validate(boxRetriever: CT600ABoxRetriever) = if (boxRetriever.lpq04().orFalse) validateAsMandatory(this) else Set()

}
