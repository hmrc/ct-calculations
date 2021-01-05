/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frsse2008.micro

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC406(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Other Income")
                                     with CtOptionalInteger with Input
                                     with SelfValidatableBox[AccountsBoxRetriever, Option[Int]] {
  override def validate(boxRetriever: AccountsBoxRetriever): Set[CtValidation] = {
    validateMoney(value)
  }
}
