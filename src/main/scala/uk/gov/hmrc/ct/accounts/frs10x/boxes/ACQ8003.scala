/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.frs102.validation.DirectorsReportEnabledCalculator
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class ACQ8003(value: Option[Boolean]) extends CtBoxIdentifier(name = "Did any of these people become a director in this period?")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever]
  with DirectorsReportEnabledCalculator {
  override def validate(boxRetriever: Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] =
    if (calculateDirectorsReportEnabled(boxRetriever))
      validateBooleanAsMandatory("ACQ8003", this)
    else
      Set.empty
}
