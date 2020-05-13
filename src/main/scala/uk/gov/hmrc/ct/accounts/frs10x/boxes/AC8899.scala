

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.frs102.validation.DirectorsReportEnabledCalculator
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class AC8899(value: Option[Boolean]) extends CtBoxIdentifier(name = "Director signing Directors' report")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever]
  with DirectorsReportEnabledCalculator {

  override def validate(boxRetriever: Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] =
    failIf(boxRetriever.directorsReportEnabled().value) (
      validateBooleanAsTrue("AC8899", this)
    )
}
