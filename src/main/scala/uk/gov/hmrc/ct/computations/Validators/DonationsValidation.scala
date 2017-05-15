package uk.gov.hmrc.ct.computations.Validators

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

trait DonationsValidation extends CtTypeConverters {

  self: ValidatableBox[ComputationsBoxRetriever] =>

  def validateLessThanTotalDonationsInPAndL(retriever: ComputationsBoxRetriever): Set[CtValidation] = {
    failIf(sumOfDonations(retriever) > retriever.cp29().value.getOrElse(0)) {
      Set(CtValidation(None, "error.sum.of.donations.exceeds.total"))
    }
  }

  def validateLessThanNetProfit(retriever: ComputationsBoxRetriever) : Set[CtValidation] = {
    failIf(retriever.cp999 > retriever.cato13) {
      Set(CtValidation(None, "error.qualifying.donations.exceeds.net.profit"))
    }
  }

  private def sumOfDonations(retriever: ComputationsBoxRetriever): Int = {
    retriever.cp303 + retriever.cp3030 + retriever.cp999
  }
}
