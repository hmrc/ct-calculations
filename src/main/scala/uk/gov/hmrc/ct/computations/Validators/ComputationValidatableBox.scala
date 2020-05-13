

package uk.gov.hmrc.ct.computations.Validators

import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}
import uk.gov.hmrc.ct.computations.CP252
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

trait ComputationValidatableBox[T <: BoxRetriever] extends ValidatableBox[T] {

  def mandatoryIfCompanyIsTrading(retriever: ComputationsBoxRetriever, boxId: String, value: Option[Int])() =
    (retriever.cpQ8().value, value) match {
      case (Some(false), None) => Set(CtValidation(boxId = Some(boxId), errorMessageKey = s"error.$boxId.fieldMustHaveValueIfTrading"))
      case _ => Set[CtValidation]()
    }

  def environmentFriendlyExpenditureCannotExceedRelevantFYAExpenditure(retriever: ComputationsBoxRetriever, value: CP252)() = {
    val relevantFYAExpenditure = retriever.cp79().orZero
    val relevantFYAExpenditureOnEnvironmentFriendly = value.orZero

    if(relevantFYAExpenditureOnEnvironmentFriendly > relevantFYAExpenditure) {
      Set(CtValidation(boxId = Some("CP252"), errorMessageKey = s"error.CP252.exceedsRelevantFYAExpenditure"))
    } else {
      Set[CtValidation]()
    }
  }

}
