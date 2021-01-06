/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v2

import org.joda.time.LocalDate
import uk.gov.hmrc.cato.time.DateHelper
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

case class E1032(value: Option[LocalDate]) extends CtBoxIdentifier("Claim exemption date") with CtOptionalDate with Input with ValidatableBox[CT600EBoxRetriever]{
  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = {
    validateAsMandatory(this) ++
      validateDateAsBetweenInclusive("E1032", this, boxRetriever.e1022().value, DateHelper.now())
  }
}
