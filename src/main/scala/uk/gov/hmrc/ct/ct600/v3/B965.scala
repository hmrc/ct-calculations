/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.RepaymentsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._

case class B965(value: Option[String]) extends CtBoxIdentifier("nominee reference")
with CtOptionalString with Input with ValidatableBox[RepaymentsBoxRetriever] {

  override def validate(boxRetriever: RepaymentsBoxRetriever): Set[CtValidation] = {
    validateStringAsMandatoryIfPAYEEQ1False(boxRetriever, "B965", this) ++
    validateOptionalStringByLength("B965", this, 1, 20) ++
    validateOptionalStringByRegex("B965", this, ValidNonForeignLessRestrictiveCharacters)
  }
}
