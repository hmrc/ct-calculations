

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class AP3(inputValue: Option[Int], defaultValue: Option[Int]) extends CtBoxIdentifier(name = "Turnover apportioned after accounting period") with CtOptionalInteger with InputWithDefault[Int] with ValidatableBox[ComputationsBoxRetriever] {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    validateZeroOrPositiveInteger(this)
  }
}

object AP3 {

  def apply(inputValue: Option[Int]): AP3 = AP3(inputValue = inputValue, defaultValue = None)
}
