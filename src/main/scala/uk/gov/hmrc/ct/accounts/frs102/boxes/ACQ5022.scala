/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class ACQ5022(value: Option[Boolean]) extends CtBoxIdentifier(name = "Other intangible assets")  with CtOptionalBoolean with Input
  with ValidatableBox[FullAccountsBoxRetriever]
{

  def validate(boxRetriever: FullAccountsBoxRetriever) = {
    import boxRetriever._
    cannotExistErrorIf(hasValue && ac42.noValue && ac43.noValue)
  }
}
