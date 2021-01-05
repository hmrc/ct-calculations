/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v2.retriever.ReturnStatementsBoxRetriever

case class RSQ4(value: Option[Boolean]) extends CtBoxIdentifier with CtOptionalBoolean with Input with ValidatableBox[ReturnStatementsBoxRetriever] {
  override def validate(boxRetriever: ReturnStatementsBoxRetriever): Set[CtValidation] = validateAsMandatory(this)
}
