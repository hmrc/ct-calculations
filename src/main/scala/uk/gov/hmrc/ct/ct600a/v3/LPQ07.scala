/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.cato.time.DateHelper
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever

case class LPQ07(value: Option[LocalDate]) extends CtBoxIdentifier(name = "When do you plan to file your return?") with CtOptionalDate with Input with ValidatableBox[CT600ABoxRetriever] {

  override def validate(boxRetriever: CT600ABoxRetriever): Set[CtValidation] = {
    failIf(boxRetriever.lpq04().orFalse) {
      validateDateAsMandatory("LPQ07", this) ++
      validateDateAsBetweenInclusive("LPQ07", this, DateHelper.now(), DateHelper.now().plusYears(2))
    }
  }

}
