/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, CtValidation, Input, ValidatableBox}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP127(value: Option[Int]) extends CtBoxIdentifier(name = "Other covid support grants") with CtOptionalInteger with Input with ValidatableBox[ComputationsBoxRetriever]  {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    if(covidSupport.doesPeriodCoverCovid(boxRetriever.cp1().value, boxRetriever.cp2().value)){
    collectErrors(
      validateAsMandatory(this),
      validateZeroOrPositiveInteger(this)
    )
    }else Set.empty
  }
}
