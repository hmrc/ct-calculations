

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.nir.NorthernIrelandRateValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP283d(value: Option[Int])
  extends CtBoxIdentifier("Main Stream Losses brought forward from on or after 01/04/2017 used against trading profit")
    with CtOptionalInteger
    with Input
    with ValidatableBox[ComputationsBoxRetriever] with NorthernIrelandRateValidation {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      requiredErrorIf(
        boxRetriever.cp283b().isPositive &&
          mayHaveNirLosses(boxRetriever) &&
          !hasValue),
      validateIntegerRange("CP283d", this, 0, boxRetriever.cp283b().orZero)
    )
  }

}

object CP283d {

  def apply(int: Int): CP283d = CP283d(Some(int))
}

