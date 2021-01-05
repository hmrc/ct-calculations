/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.TotalNetAssetsLiabilitiesCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.validation.AssetsEqualToSharesValidator
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger, CtValidation}

case class AC68(value: Option[Int]) extends CtBoxIdentifier(name = "Total net assets or liabilities (current PoA)")
  with CtOptionalInteger with AssetsEqualToSharesValidator with ValidatableBox[Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever] {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    validateAssetsEqualToShares("AC68", boxRetriever.ac80(), boxRetriever.companyType().isLimitedByGuarantee)
  }
}

object AC68 extends Calculated[AC68, Frs102AccountsBoxRetriever] with TotalNetAssetsLiabilitiesCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC68 = {
    import boxRetriever._
    calculateCurrentTotalNetAssetsLiabilities(ac62(), ac64(), ac66(), ac150B())
  }
}
