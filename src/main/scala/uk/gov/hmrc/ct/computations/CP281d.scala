

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP281d(value: Option[Int])
  extends CtBoxIdentifier("Mainstream losses brought forward from on or after 01/04/2017")
  with CtOptionalInteger
  with Input
  with ValidatableBox[ComputationsBoxRetriever] {

  override def validate(retriever: ComputationsBoxRetriever): Set[CtValidation] = {
    import uk.gov.hmrc.ct.computations.losses._
    collectErrors(
      requiredErrorIf(retriever.cpQ117().isTrue &&
        lossReform2017Applies(retriever.cp2()) &&
        !hasValue),
      cannotExistErrorIf(hasValue &&
        (retriever.cpQ117().isFalse ||
          !lossReform2017Applies(retriever.cp2())
        )
      ),
      sumOfLossesBroughtForwardError(retriever)
    )
  }

  private def sumOfLossesBroughtForwardError(retriever: ComputationsBoxRetriever) = {
    failIf(retriever.cpQ117().isTrue && this.orZero + retriever.cp281c().orZero != retriever.cp281b().orZero){
      Set(CtValidation(None,"error.CP281.breakdown.sum.incorrect"))
    }
  }
}

object CP281d extends Calculated[CP281d, ComputationsBoxRetriever] {

  override def calculate(boxRetriever: ComputationsBoxRetriever): CP281d = {
    import boxRetriever._
    CP281d(cp281b().value.map { postReformLosses =>
      postReformLosses - cp281c().orZero
    })
  }

  def apply(int: Int): CP281d = CP281d(Some(int))
}
