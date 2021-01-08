/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever._

case class ACQ5021(value: Option[Boolean]) extends CtBoxIdentifier(name = "Goodwill")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[FullAccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: FullAccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._
    collectErrors(

      cannotExistErrorIf(hasValue && ac42.noValue && ac43.noValue),

      failIf(anyHaveValue(ac42, ac43)) {
        atLeastOneBoxHasValue("balance.sheet.intangible.assets", this, acq5022)
      }
    )
  }
}
