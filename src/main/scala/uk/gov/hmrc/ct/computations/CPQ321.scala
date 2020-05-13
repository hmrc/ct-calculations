

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.Validators.DonationsValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import CP3020.grassrootsStart

case class CPQ321(value: Option[Boolean]) extends CtBoxIdentifier(name = "Did your company make any donations to grassroots sports?")
  with CtOptionalBoolean with Input with ValidatableBox[ComputationsBoxRetriever] with DonationsValidation {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      failIf(!boxRetriever.cp2.value.isBefore(grassrootsStart) && noValue) {
        Set(CtValidation(Some("CPQ321"), s"error.CPQ321.required"))
      },
      validateLessThanTotalDonationsInPAndL(boxRetriever),
      validateLessThanNetProfit(boxRetriever),
      failIf(isTrue && !boxRetriever.cp3010.isPositive && !boxRetriever.cp3020.isPositive && !boxRetriever.cp3030.isPositive) {
        Set(CtValidation(None, "error.CPQ321.no.grassroots.donations"))
      }
    )
  }
}
