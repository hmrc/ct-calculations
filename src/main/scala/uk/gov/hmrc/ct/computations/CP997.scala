

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.Validators.TradingLossesValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP997(value: Option[Int]) extends CP997Abstract(value)
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with TradingLossesValidation {

  override def validate(retriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      requiredErrorIf(retriever.cp281b().isPositive && !hasValue),
      validateZeroOrPositiveInteger(this),
      cp997ExceedsNonTradingProfitAfterCPQ19(retriever),
      exceedsNonTradingProfitErrors(retriever),
      lossesAlreadyUsed(retriever)
    )
  }

  private def exceedsNonTradingProfitErrors(retriever: ComputationsBoxRetriever) = {
    failIf(retriever.cato01() < this.orZero) {
      Set(CtValidation(Some("CP997"), "error.CP997.exceeds.nonTradingProfit"))
    }
  }

  private def cp997ExceedsNonTradingProfitAfterCPQ19(retriever: ComputationsBoxRetriever) = {
    failIf(retriever.cp44() > 0 && retriever.cp44() < this.orZero){
      Set(CtValidation(Some("CP997"), "error.CP997.exceeds.nonTradingProfit"))
    }
  }
  private def lossesAlreadyUsed(retriever: ComputationsBoxRetriever) = {
    failIf(retriever.cp281b().orZero - retriever.cp283b().orZero < this.orZero){
      Set(CtValidation(Some("CP997"), "error.CP997.exceeds.leftLosses"))
    }
  }
}

object CP997 {

  def apply(int: Int): CP997 = CP997(Some(int))

}
//CP997 saying “cannot exceed CP281b minus CP283b