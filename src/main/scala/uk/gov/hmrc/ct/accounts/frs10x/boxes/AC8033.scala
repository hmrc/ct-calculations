/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC8033(value: Option[String]) extends CtBoxIdentifier(name = "Secretary name") with CtOptionalString with Input with ValidatableBox[Frs10xDirectorsBoxRetriever] {

  override def validate(boxRetriever: Frs10xDirectorsBoxRetriever): Set[CtValidation] = {
    validateOptionalStringByLength("AC8033", this, 1, 40) ++ validateCoHoStringReturnIllegalChars("AC8033", this)
  }
}
