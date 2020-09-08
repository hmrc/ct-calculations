/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC415(value: Option[Int]) extends CtBoxIdentifier(name = "Staff costs (current PoA)")
  with CtOptionalInteger
  with Input
  with ValidatableBox[AccountsBoxRetriever]
  with Debit {

  override def validate(boxRetriever: AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateMoney(value)
    )
  }
}
