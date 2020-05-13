

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.RepaymentsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._

case class B955(value: Option[String]) extends CtBoxIdentifier("payee name")
                                       with CtOptionalString with Input  with ValidatableBox[RepaymentsBoxRetriever] {

  override def validate(boxRetriever: RepaymentsBoxRetriever): Set[CtValidation] = {
    validateStringAsMandatoryIfPAYEEQ1False(boxRetriever, "B955", this) ++
    validateOptionalStringByLength("B955" , this, 2, 56) ++
    validateOptionalStringByRegex("B955", this, ValidNonForeignLessRestrictiveCharacters)
  }
}
