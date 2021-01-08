/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts.frs105.calculations.TotalNetAssetsLiabilitiesCalculator
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.validation.AssetsEqualToSharesValidator
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class AC69(value: Option[Int]) extends CtBoxIdentifier(name = "Total net assets or liabilities (previous PoA)")
  with CtOptionalInteger
  with ValidatableBox[Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever]
  with AssetsEqualToSharesValidator {

  override def validate(boxRetriever: Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {

    validateAssetsEqualToShares("AC69", boxRetriever.ac491(), boxRetriever.companyType().isLimitedByGuarantee)
  }
}

object AC69 extends Calculated[AC69, Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever] with TotalNetAssetsLiabilitiesCalculator {

  override def calculate(boxRetriever: Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever): AC69 = {
    import boxRetriever._
    calculatePreviousTotalNetAssetsLiabilities(ac63(), ac65(), ac67(), ac471())
  }
}
