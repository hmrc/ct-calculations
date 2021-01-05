/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.Validators.DonationsValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CPQ21(value: Option[Boolean]) extends CtBoxIdentifier(name = "Donations made?")
  with CtOptionalBoolean with Input with ValidatableBox[ComputationsBoxRetriever] with DonationsValidation {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateBooleanAsMandatory("CPQ21", this),
      validateLessThanTotalDonationsInPAndL(boxRetriever),
      validateLessThanNetProfit(boxRetriever),
      validateHasCharitableDonations(this, boxRetriever)
    )
  }

  private def validateHasCharitableDonations(box: CtOptionalBoolean, retriever: ComputationsBoxRetriever): Set[CtValidation] = {
    failIf(box.isTrue && !retriever.cp301().isPositive && !retriever.cp302().isPositive && !retriever.cp303().isPositive) {
      Set(CtValidation(None, "error.CPQ21.no.charitable.donations"))
    }
  }
}
