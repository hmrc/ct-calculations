package uk.gov.hmrc.ct.computations.Validators

import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.box.{CtOptionalInteger, CtValidation, ValidatableBox}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

trait ComputationValidatableBox[T <: BoxRetriever] extends ValidatableBox[T] {

  def fieldhasValueWhenTrading(retriever: ComputationsBoxRetriever, boxId: String, value: Option[Int]) =
    (retriever.retrieveCPQ8().value, value) match {
      case (Some(false), None) => Set(CtValidation(boxId = Some(boxId), errorMessageKey = s"error.$boxId.fieldMustHaveValueIfTrading"))
      case _ => Set()
    }
}
