/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, CtValidation, Input, ValidatableBox}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP120(value: Option[Boolean]) extends CtBoxIdentifier(name = "Did you have Eat Out to Help Out Scheme") with CtOptionalBoolean with Input with ValidatableBox[ComputationsBoxRetriever] {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    val apStartDate = boxRetriever.cp1().value
    val apEndDate = boxRetriever.cp2().value

    def validateAsMandatoryIfInDate() = {
      if(covidSupport.doesPeriodCoverEotho(apStartDate, apEndDate) && !boxRetriever.acq8999a().value.getOrElse(false)){
        validateAsMandatory(this)()
      } else {
        validationSuccess
      }
    }

    collectErrors(validateAsMandatoryIfInDate)
  }
}
