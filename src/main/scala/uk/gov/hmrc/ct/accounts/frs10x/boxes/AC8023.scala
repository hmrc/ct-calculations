/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.frs102.validation.DirectorsReportExistenceValidation
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class AC8023(value: Option[Boolean])
  extends CtBoxIdentifier(name = "Do you want to file a directors' report to HMRC?")
  with CtOptionalBoolean
  with Input
  with SelfValidatableBox[Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever, Option[Boolean]]
  with DirectorsReportExistenceValidation {

  override def validate(boxRetriever: Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    val isMicroHmrcFiling = boxRetriever.hmrcFiling().value && boxRetriever.microEntityFiling().value

      collectErrors(
        failIf(isMicroHmrcFiling) {
          validateAsMandatory()
        },
        cannotExistErrorIf(!isMicroHmrcFiling && hasValue)
      )
  }
}
