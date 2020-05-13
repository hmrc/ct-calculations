

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

case class E27(value: Option[Int]) extends CtBoxIdentifier("Value of any non-qualifying investments and loans") with CtOptionalInteger with Input with ValidatableBox[CT600EBoxRetriever] {
  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = {
    failIf (boxRetriever.e26().value.contains(CharityLoansAndInvestments.SomeLoansAndInvestments)) {
      value match {
        case None => Set(CtValidation(Some(id), s"error.$id.required"))
        case Some(x) if x == 0 => Set(CtValidation(Some(id), s"error.$id.required"))
        case Some(x) if x < 0 => Set(CtValidation(Some(id), s"error.$id.mustBePositive"))
        case _ => Set()
      }
    }
  }
}
