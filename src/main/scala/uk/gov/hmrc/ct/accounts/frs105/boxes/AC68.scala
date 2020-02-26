/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts.frs105.calculations.TotalNetAssetsLiabilitiesCalculator
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.validation.AssetsEqualToSharesValidator
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class AC68(value: Option[Int]) extends CtBoxIdentifier(name = "Total net assets or liabilities (current PoA)")
  with CtOptionalInteger
  with ValidatableBox[Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever]
  with AssetsEqualToSharesValidator {

  override def validate(boxRetriever: Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    validateAssetsEqualToShares("AC68", boxRetriever.ac490(), boxRetriever.companyType().isLimitedByGuarantee)
  }
}

object AC68 extends Calculated[AC68, Frs105AccountsBoxRetriever] with TotalNetAssetsLiabilitiesCalculator {

  override def calculate(boxRetriever: Frs105AccountsBoxRetriever): AC68 = {
    import boxRetriever._
    calculateCurrentTotalNetAssetsLiabilities(ac62(), ac64(), ac66(), ac470())
  }
}
