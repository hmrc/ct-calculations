/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class AC70(value: Option[Int]) extends CtBoxIdentifier(name = "Called up share capital (current PoA)")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever]
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    val limitedByGuarantee = boxRetriever.companyType().isLimitedByGuarantee
    collectErrors(
      failIf(limitedByGuarantee) {
        cannotExistErrorIf(value.nonEmpty)
      },
      failIf(!limitedByGuarantee)(
        collectErrors(
          validateAsMandatory(this),
          validateMoney(value, min = 1)
        )
      )
    )
  }
}
