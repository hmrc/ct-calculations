/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC470(value: Option[Int]) extends CtBoxIdentifier(name = "Accruals and deferred income (current PoA)")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs105AccountsBoxRetriever]
  with Debit {

  override def validate(boxRetriever: Frs105AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateMoney(value, min = 0)
    )
  }
}
