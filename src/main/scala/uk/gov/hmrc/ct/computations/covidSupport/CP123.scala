/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.covidSupport

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP123(value: Option[Int]) extends CtBoxIdentifier(name = "CJRS (Coronavirus Job Retention Scheme) entitlement") with CtOptionalInteger with Input with ValidatableBox[ComputationsBoxRetriever] {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    if (doesPeriodCoverCovid(boxRetriever.cp1().value, boxRetriever.cp2().value)) {
      collectErrors(
        validateAsMandatory(this),
        validateZeroOrPositiveInteger(this)
      )
    } else Set.empty
  }
}
