/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.TotalShareholdersFundsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.validation.AssetsEqualToSharesValidator
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger, CtValidation}

case class AC81(value: Option[Int]) extends CtBoxIdentifier(name = "Total Shareholders Funds (previous PoA)")
  with CtOptionalInteger with AssetsEqualToSharesValidator with ValidatableBox[Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever] {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    validateAssetsEqualToShares("AC81", boxRetriever.ac69(), boxRetriever.companyType().isLimitedByGuarantee)
  }
}

object AC81 extends Calculated[AC81, Frs102AccountsBoxRetriever] with TotalShareholdersFundsCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC81 = {
    import boxRetriever._
    calculatePreviousTotalShareholdersFunds(ac71(), ac77(), ac75())
  }
}
