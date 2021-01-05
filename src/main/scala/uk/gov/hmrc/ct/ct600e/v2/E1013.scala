/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

case class E1013(value: Option[Boolean]) extends CtBoxIdentifier("E3 Included Amounts") with CtOptionalBoolean with Input with ValidatableBox[CT600EBoxRetriever] {

  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = {
    validateConditionalRequired(boxRetriever)
  }

  private def validateConditionalRequired(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = {
    val e1 = boxRetriever.e1().orZero
    val e2 = boxRetriever.e2().orZero

    value match {
      case None if e2 > e1 => Set(CtValidation(Some("E1013"), s"error.E1013.conditionalRequired"))
      case _ => Set()
    }
  }
}
