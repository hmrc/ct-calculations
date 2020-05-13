

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.CP287
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v3.retriever.AboutThisReturnBoxRetriever


case class B45(value: Option[Boolean]) extends CtBoxIdentifier("Are you owed a repayment for an earlier period?")
  with CtOptionalBoolean {
}

object B45 extends Calculated[B45, AboutThisReturnBoxRetriever] with CorporationTaxCalculator {

  override def calculate(fieldValueRetriever: AboutThisReturnBoxRetriever): B45 = {
    fieldValueRetriever match {
      case computationsBoxRetriever: ComputationsBoxRetriever =>  defaultSetIfLossCarriedForward(fieldValueRetriever.b45Input(), computationsBoxRetriever.cp287())
      case _ =>  defaultSetIfLossCarriedForward(fieldValueRetriever.b45Input(), CP287(None))
    }
  }
}
