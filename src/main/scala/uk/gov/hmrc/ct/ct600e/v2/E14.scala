

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

case class E14(value: Option[Int]) extends CtBoxIdentifier("Trading costs in relation to exempt activities") with CtOptionalInteger with Input with ValidatableBox[CT600EBoxRetriever] {
  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = {
    (boxRetriever.e5().value, value) match {
      case (Some(x), _) if x > 0 => validateZeroOrPositiveInteger(this)
      case (_, Some(x)) => Set(CtValidation(Some("E14"), "error.E14.conditionalMustBeEmpty"))
      case _ => Set()
    }
  }
}
