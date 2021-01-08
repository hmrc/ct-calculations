/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC8053(value: Option[String]) extends CtBoxIdentifier(name = "Company policy on disabled employees") with CtOptionalString with Input with ValidatableBox[Frs10xDirectorsBoxRetriever] {
  override def validate(boxRetriever: Frs10xDirectorsBoxRetriever): Set[CtValidation] =
    validateOptionalStringByLength("AC8053", this, 0, StandardCohoTextFieldLimit) ++ validateCoHoStringReturnIllegalChars("AC8053", this)
}
