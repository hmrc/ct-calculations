/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.approval.boxes

import org.joda.time.LocalDate
import uk.gov.hmrc.cato.time.DateHelper
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC198A(value: Option[LocalDate]) extends CtBoxIdentifier("Approve accounts date of approval") with CtOptionalDate with Input with ValidatableBox[AccountsBoxRetriever] {

  override def validate(boxRetriever: AccountsBoxRetriever): Set[CtValidation] = {

    collectErrors(
      validateDateAsMandatory("AC198A", this),
      validateDateAsBetweenInclusive("AC198A", this, boxRetriever.ac4().value.plusDays(1), DateHelper.now())
    )
  }
}
