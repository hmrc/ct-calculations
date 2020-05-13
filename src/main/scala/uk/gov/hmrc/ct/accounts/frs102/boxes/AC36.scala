

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.ProfitOrLossFinancialYearCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.accounts.frs10x.retriever.{Frs10xDormancyBoxRetriever, Frs10xFilingQuestionsBoxRetriever}
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class AC36(value: Option[Int]) extends CtBoxIdentifier(name = "Profit or loss for financial year (current PoA)")
  with CtOptionalInteger
  with ValidatableBox[Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever with Frs10xDormancyBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever with Frs10xDormancyBoxRetriever): Set[CtValidation] = {
    failIf(!boxRetriever.acq8999().orFalse && (boxRetriever.hmrcFiling().value || boxRetriever.acq8161().orFalse))(
      boxRetriever match {
        case br: FullAccountsBoxRetriever => validateFull(br)
        case _ => validateAbridged(boxRetriever)
      }
    )
  }

  private def validateFull(boxRetriever: FullAccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._
    atLeastOneBoxHasValue("profit.loss", ac12(), ac14(), ac18(), ac20(), ac28(), ac30(), ac34())
  }

  private def validateAbridged(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._
    atLeastOneBoxHasValue("profit.loss", ac16(), ac18(), ac20(), ac28(), ac30(), ac34())
  }

}

object AC36 extends Calculated[AC36, Frs102AccountsBoxRetriever] with ProfitOrLossFinancialYearCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC36 = {
    import boxRetriever._
    calculateAC36(ac32(), ac34())
  }
}
