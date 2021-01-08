/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP510(value: Option[Int]) extends CtBoxIdentifier(name = "Unallowable expenses") with CtOptionalInteger
  with Input with ValidatableBox[ComputationsBoxRetriever] {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateZeroOrPositiveInteger(this),
      unAllowableExpensesError(boxRetriever)
    )
  }

  private def unAllowableExpensesError(boxRetriever: ComputationsBoxRetriever) = {
    failIf(hasValue && value.get > boxRetriever.cp508().value) {
      Set(CtValidation(None, "block.incomeFromProperty.unAllowable.error"))
    }
  }

}

object CP510 {
  def apply(value: Int): CP510 = CP510(Some(value))
}
