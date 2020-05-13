

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.RepaymentsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._

case class B935(value: String) extends CtBoxIdentifier("account name")
with CtString with Input with ValidatableBox[RepaymentsBoxRetriever] {

  override def validate(boxRetriever: RepaymentsBoxRetriever): Set[CtValidation] = {
    validateAllFilledOrEmptyStringsForBankDetails(boxRetriever,"B935") ++
      validateStringByLength("B935", this, 2, 28) ++
      validateStringByRegex("B935", this, ValidNonForeignLessRestrictiveCharacters)
  }
}
