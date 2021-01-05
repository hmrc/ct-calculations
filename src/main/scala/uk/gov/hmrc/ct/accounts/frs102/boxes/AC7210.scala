/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC7210(value: Option[String]) extends CtBoxIdentifier(name = "Dividends note - Additional information")
                                      with CtOptionalString
                                      with Input
                                      with SelfValidatableBox[Frs102AccountsBoxRetriever, Option[String]] {


  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      cannotExistErrorIf(hasValue && !boxRetriever.ac32().hasValue),
      validateOptionalStringByLength(min = 0, max = StandardCohoTextFieldLimit),
      validateCoHoStringReturnIllegalChars()
    )
  }
}
