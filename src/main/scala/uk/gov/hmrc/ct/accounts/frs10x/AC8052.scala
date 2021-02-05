/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x

import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC8052(value: Option[String]) extends CtBoxIdentifier(name = "Political and charitable donations") with CtOptionalString with Input with ValidatableBox[Frs10xDirectorsBoxRetriever] {
  override def validate(boxRetriever: Frs10xDirectorsBoxRetriever): Set[CtValidation] =
    validateOptionalStringByLength("AC8052", this, 0, StandardCohoTextFieldLimit) ++ validateCoHoStringReturnIllegalChars("AC8052", this)
}
