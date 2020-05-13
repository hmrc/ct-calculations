/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.validation.AssetsEqualToSharesValidator
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class AC490(value: Option[Int]) extends CtBoxIdentifier(name = "Capital and reserves (current PoA)")
  with CtOptionalInteger
  with Input
  with SelfValidatableBox[Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever, Option[Int]]
  with AssetsEqualToSharesValidator {

  override def validate(boxRetriever: Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    import boxRetriever._

    collectErrors(
      validateAsMandatory(),
      validateAssetsEqualToShares("AC490", ac68(), boxRetriever.companyType().isLimitedByGuarantee),
      validateMoney(value)
    )
  }
}
