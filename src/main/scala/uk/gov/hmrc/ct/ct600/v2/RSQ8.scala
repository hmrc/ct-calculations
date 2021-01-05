/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v2.retriever.ReturnStatementsBoxRetriever
import uk.gov.hmrc.ct.ct600.v2.validation.RSQ7MutuallyExclusiveWithRSQ8

case class RSQ8(value: Option[Boolean]) extends CtBoxIdentifier
  with CtOptionalBoolean with Input with ValidatableBox[ReturnStatementsBoxRetriever] with RSQ7MutuallyExclusiveWithRSQ8 {

  override def validate(boxRetriever: ReturnStatementsBoxRetriever): Set[CtValidation] = {
    validateAsMandatory(this) ++ validateMutualExclusivity(boxRetriever)
  }
}
