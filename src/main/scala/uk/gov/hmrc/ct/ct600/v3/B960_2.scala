/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.RepaymentsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._

case class B960_2(value: Option[String]) extends CtBoxIdentifier("Payee Address Line 2")
    with CtOptionalString with Input with ValidatableBox[RepaymentsBoxRetriever] {

  override def validate(boxRetriever: RepaymentsBoxRetriever): Set[CtValidation] = {
    validateStringAsMandatoryIfPAYEEQ1False(boxRetriever, "B960_2", this)  ++
    validateOptionalStringByLength("B960_2", this, 1, 28) ++
    validateOptionalStringByRegex("B960_2", this, ValidNonForeignMoreRestrictiveCharacters)
  }
}
