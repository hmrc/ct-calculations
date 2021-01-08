/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frsse2008

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC13(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Turnover/Sales")
                                    with CtOptionalInteger with Input
                                    with SelfValidatableBox[AccountsBoxRetriever, Option[Int]] {
  override def validate(boxRetriever: AccountsBoxRetriever): Set[CtValidation] = {
    val errors = collectErrors(
      validateZeroOrPositiveInteger(this)
    )

    if(errors.isEmpty) {
      validateMoney(value, 0)
    } else {
      errors
    }
  }
}
