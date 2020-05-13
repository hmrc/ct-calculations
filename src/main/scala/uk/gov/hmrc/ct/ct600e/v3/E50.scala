

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

case class E50(value: Option[Int]) extends CtBoxIdentifier("Income Total turnover from exempt charitable trading activities") with CtOptionalInteger with Input with ValidatableBox[CT600EBoxRetriever] {
  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = {
    import boxRetriever._
    (e95(), e50()) match {
      case (_, e50) if e50.orZero < 0 => Set(CtValidation(boxId = Some("E50"), errorMessageKey = "error.E50.mustBeZeroOrPositive"))
      case (E95(Some(e95)), e50) if e95 > 0 && e50.orZero <= 0 => Set(CtValidation(boxId = Some("E50"), errorMessageKey = "error.E50.must.be.positive.when.E95.positive"))
      case _ => Set.empty
    }
  }
}
