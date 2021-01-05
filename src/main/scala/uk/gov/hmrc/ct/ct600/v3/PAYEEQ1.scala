/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.RepaymentsBoxRetriever

case class PAYEEQ1(value: Option[Boolean]) extends CtBoxIdentifier("Repayment to than company?")
with CtOptionalBoolean with Input with ValidatableBox[RepaymentsBoxRetriever] {

  override def validate(boxRetriever: RepaymentsBoxRetriever): Set[CtValidation] = validateBooleanAsMandatory("PAYEEQ1", this)
}
