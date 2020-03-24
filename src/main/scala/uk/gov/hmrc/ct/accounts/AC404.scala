/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class AC404(value: Option[Int]) extends CtBoxIdentifier(name = "Deductions from OPW")
with CtOptionalInteger
with Input
with ValidatableBox[AccountsBoxRetriever with FilingAttributesBoxValueRetriever] {
  override def validate(boxRetriever: AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = Set.empty
}

object AC404 {
  def apply(value: Int): AC404 = AC404(Some(value))
}