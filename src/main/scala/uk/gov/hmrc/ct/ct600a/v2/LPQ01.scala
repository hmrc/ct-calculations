package uk.gov.hmrc.ct.ct600a.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoolean, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.v2.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever

case class LPQ01(value: Boolean) extends CtBoxIdentifier(name = "Declare loans to participators") with CtBoolean

object LPQ01 extends Calculated[LPQ01, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(boxRetriever: CT600ABoxRetriever): LPQ01 = {
    calculateLPQ01(boxRetriever.retrieveLPQ03, boxRetriever.retrieveLPQ04, boxRetriever.retrieveLPQ05)
  }

}
