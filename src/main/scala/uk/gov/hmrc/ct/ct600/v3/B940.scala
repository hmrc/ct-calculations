/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.RepaymentsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._

case class B940(value: Option[String]) extends CtBoxIdentifier("building society reference")
    with CtOptionalString with Input with ValidatableBox[RepaymentsBoxRetriever] {

  override def validate(boxRetriever: RepaymentsBoxRetriever): Set[CtValidation] = {
    validateOptionalStringByLength("B940", this, 2, 18) ++
    validateOptionalStringByRegex("B940", this, ValidNonForeignLessRestrictiveCharacters)
  }
}
