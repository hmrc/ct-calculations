

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

case class E60(value: Option[Int]) extends CtBoxIdentifier("Income UK land and buildings – excluding any amounts included on form CT600") with CtOptionalInteger with Input with ValidatableBox[CT600EBoxRetriever] {
  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    (e100(), e60()) match {
      case (_, e60) if e60.orZero < 0 => Set(CtValidation(boxId = Some("E60"), errorMessageKey = "error.E60.mustBeZeroOrPositive"))
      case (E100(Some(e100)), e60) if e100 > 0 && e60.orZero <= 0 => Set(CtValidation(boxId = Some("E60"), errorMessageKey = "error.E60.must.be.positive.when.E100.positive"))
      case _ => Set.empty
    }
  }
}
