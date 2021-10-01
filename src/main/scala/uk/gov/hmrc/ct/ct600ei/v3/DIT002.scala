/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600ei.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, CtValidation, Input, ValidatableBox, Validators}
import uk.gov.hmrc.ct.ct600ei.v3.retriever.CT600EiBoxRetriever

case class DIT002(value: Option[Boolean]) extends CtBoxIdentifier(name = "Did the company export goods or services?")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[CT600EiBoxRetriever]
  with Validators {

  override def validate(boxRetriever: CT600EiBoxRetriever): Set[CtValidation] = {
    (boxRetriever.dit001().value, boxRetriever.dit003().value, value) match {
      case (Some(true), Some(false), Some(false)) => Set(CtValidation(Some("DIT002"), "error.DIT002.required"), CtValidation(Some("DIT003"), "error.DIT002.required"))
      case _ => Set.empty
    }
  }
}

object DIT002 {
  def apply(value: Boolean): DIT002 = DIT002(Some(value))
}


