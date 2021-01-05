/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP503(value: Option[Int]) extends CtBoxIdentifier(name = "Income from property expenses") with CtOptionalInteger with Input with ValidatableBox[ComputationsBoxRetriever] {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = validateZeroOrPositiveInteger(this) ++ validateNotExceedingCP501(this, boxRetriever)

  private def validateNotExceedingCP501(box: CP503, retriever: ComputationsBoxRetriever): Set[CtValidation] = {
    (box.value, retriever.cp501().value) match {
      case (Some(x), Some(y)) if x > y => Set(CtValidation(Some(box.id), s"error.${box.id}.propertyExpensesExceedsIncome"))
      case (Some(x), None) if x > 0 => Set(CtValidation(Some(box.id), s"error.${box.id}.propertyExpensesExceedsIncome"))
      case _ => Set()
    }
  }
}
