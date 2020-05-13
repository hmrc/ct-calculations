

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.RepaymentsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._


case class B920(value: String) extends CtBoxIdentifier("bank/BS name")
with CtString with Input with ValidatableBox[RepaymentsBoxRetriever] {

  override def validate(boxRetriever: RepaymentsBoxRetriever): Set[CtValidation] = {
    validateAllFilledOrEmptyStringsForBankDetails(boxRetriever,"B920") ++
      validateStringByLength("B920", this, 2, 56) ++
      validateStringByRegex("B920", this, ValidNonForeignLessRestrictiveCharacters)
  }
}
