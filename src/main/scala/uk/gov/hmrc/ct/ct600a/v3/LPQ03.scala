/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever

case class LPQ03(value: Option[Boolean]) extends CtBoxIdentifier(name = "During this accounting period, did the company make any loans to participators or their associates that were not repaid") with CtOptionalBoolean with Input with ValidatableBox[CT600ABoxRetriever] {
  override def validate(boxRetriever: CT600ABoxRetriever): Set[CtValidation] = {

    failIf(boxRetriever.lpq04().orFalse) {
      validateBooleanAsMandatory("LPQ03", this)
    }
  }
}
