/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

case class E4(value: Option[Int]) extends CtBoxIdentifier("Amounts overclaimed for period") with CtOptionalInteger with Input with ValidatableBox[CT600EBoxRetriever] {


  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = {
    validateConditionalRequired(boxRetriever)
  }

  private def validateConditionalRequired(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = {
    val e1 = boxRetriever.e1().orZero
    val e2 = boxRetriever.e2().orZero

    value match {
      case None if e2 < e1 => Set(CtValidation(Some("E4"), s"error.E4.conditionalRequired"))
      case Some(x) if e1 < e2 => Set(CtValidation(Some("E4"), s"error.E4.conditionalMustBeEmpty"))
      case Some(x) if x < 1 => Set(CtValidation(Some("E4"), s"error.E4.outOfRange"))
      case _ => Set()
    }
  }
}
