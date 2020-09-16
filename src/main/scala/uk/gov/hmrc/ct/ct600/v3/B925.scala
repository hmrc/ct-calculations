/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.RepaymentsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._


case class B925(value: String) extends CtBoxIdentifier("sort code")
with CtString with Input with ValidatableBox[RepaymentsBoxRetriever] {

  override def validate(boxRetriever: RepaymentsBoxRetriever): Set[CtValidation] = {
      validateAllFilledOrEmptyStringsForBankDetails(boxRetriever, "B925") ++
      validateStringByRegex("B925", this, SortCodeValidChars)
  }
}
