/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

case class E15(value: Option[Int]) extends CtBoxIdentifier("Expenditure on UK land and buildings in relation to exempt activities") with CtOptionalInteger with Input with ValidatableBox[CT600EBoxRetriever] {
  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = {
    (boxRetriever.e7().value, value) match {
      case (Some(x), _) if x > 0 => validateZeroOrPositiveInteger(this)
      case (_, Some(x)) => Set(CtValidation(Some("E15"), "error.E15.conditionalMustBeEmpty"))
      case _ => Set()
    }
  }
}
