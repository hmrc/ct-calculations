

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.RepaymentsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._

case class B960_3(value: Option[String]) extends CtBoxIdentifier("Payee Address Line 3")
    with CtOptionalString with Input with ValidatableBox[RepaymentsBoxRetriever] {

  override def validate(boxRetriever: RepaymentsBoxRetriever): Set[CtValidation] = {
    validateOptionalStringByLength("B960_3", this, 1, 28) ++
    validateOptionalStringByRegex("B960_3", this, ValidNonForeignMoreRestrictiveCharacters)
  }
}
