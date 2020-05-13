

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.Validators.TradingLossesValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CPQ17(value: Option[Boolean])
  extends CtBoxIdentifier(name = "Trading losses not used from previous accounting periods?")
    with CtOptionalBoolean
    with Input
    with ValidatableBox[ComputationsBoxRetriever]
    with TradingLossesValidation {
  import losses._

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    val lossesReformApplies = lossReform2017Applies(boxRetriever.cp2())

    collectErrors(
      requiredErrorIf({ value.isEmpty && (hasTradingProfit(boxRetriever) ||
        (lossesReformApplies && hasNonTradingProfit(boxRetriever))
        )}),
      cannotExistErrorIf(value.nonEmpty && (noTradingProfit(boxRetriever) &&
        !(lossesReformApplies && !noNonTradingProfit(boxRetriever))
        ))
    )
  }
}
