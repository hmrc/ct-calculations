/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.covidSupport

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.covidSupport
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP122(value: Option[Int]) extends CtBoxIdentifier(name = "CJRS (Coronavirus Job Retention Scheme) received") with CtOptionalInteger with Input with ValidatableBox[ComputationsBoxRetriever] {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    val cp123 = boxRetriever.cp123().value.getOrElse(0)
    val cp124 = boxRetriever.cp124().value.getOrElse(0)
    val entitlementAndOverpaymentTotal = cp123 + cp124

    if(covidSupport.doesPeriodCoverCovid(boxRetriever.cp1().value, boxRetriever.cp2().value)) {
      collectErrors(
        validateAsMandatory(this),
        belowMin(this.value, entitlementAndOverpaymentTotal)
      )
    } else Set.empty
  }
}
