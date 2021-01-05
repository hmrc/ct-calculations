/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC8051(value: Option[String]) extends CtBoxIdentifier(name = "Principal activities of the company") with CtOptionalString with Input with ValidatableBox[Frs10xDirectorsBoxRetriever] {
  override def validate(boxRetriever: Frs10xDirectorsBoxRetriever): Set[CtValidation] =
    validateOptionalStringByLength("AC8051", this, 0, 250) ++ validateCoHoStringReturnIllegalChars("AC8051", this)
}
