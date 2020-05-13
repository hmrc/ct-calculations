

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.Validators.ComputationValidatableBox
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever


case class CP252(value: Option[Int]) extends CtBoxIdentifier("Expenditure on designated environmentally friendly machinery and plant") with CtOptionalInteger with Input with ComputationValidatableBox[ComputationsBoxRetriever] {

  override def validate(boxRetriever: ComputationsBoxRetriever) = {
    validateZeroOrPositiveInteger(this) ++
      environmentFriendlyExpenditureCannotExceedRelevantFYAExpenditure(boxRetriever, this) ++
      cannotExistErrorIf(hasValue && boxRetriever.cpQ8().isTrue) ++
      cannotExistErrorIf(hasValue && boxRetriever.cp1().value.isAfter(new LocalDate("2020-03-31")))
  }
}

object CP252 {

  def apply(value: Int): CP252 = CP252(Some(value))
}
