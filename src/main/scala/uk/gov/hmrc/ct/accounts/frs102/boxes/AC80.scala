

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.TotalShareholdersFundsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.validation.AssetsEqualToSharesValidator
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class AC80(value: Option[Int]) extends CtBoxIdentifier(name = "Total Shareholders Funds (current PoA)")
  with CtOptionalInteger with AssetsEqualToSharesValidator with ValidatableBox[Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever] {
 
  override def validate(boxRetriever: Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    validateAssetsEqualToShares("AC80", boxRetriever.ac68(), boxRetriever.companyType().isLimitedByGuarantee)
  }
}

object AC80 extends Calculated[AC80, Frs102AccountsBoxRetriever] with TotalShareholdersFundsCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC80 = {
    import boxRetriever._
    calculateCurrentTotalShareholdersFunds(ac70(), ac76(), ac74())
  }
}
