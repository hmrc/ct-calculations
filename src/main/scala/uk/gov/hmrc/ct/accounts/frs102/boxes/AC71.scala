/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class AC71(value: Option[Int]) extends CtBoxIdentifier(name = "Called up share capital (previous PoA)")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever]
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    val limitedByGuarantee = boxRetriever.companyType().isLimitedByGuarantee
    val hasPY = boxRetriever.ac205().hasValue
    collectErrors(
      failIf(!hasPY || limitedByGuarantee)(
        cannotExistErrorIf(value.nonEmpty)
      ),
      failIf(hasPY && !limitedByGuarantee)(
        collectErrors(
          validateAsMandatory(this),
          validateMoney(value, min = 1)
        )
      )
    )
  }

}
