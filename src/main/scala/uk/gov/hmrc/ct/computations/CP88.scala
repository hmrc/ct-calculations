

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.Validators.ComputationValidatableBox
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP88(value: Option[Int]) extends CtBoxIdentifier(name = "Annual Investment Allowance") with CtOptionalInteger with Input with ComputationValidatableBox[ComputationsBoxRetriever] {
  override def validate(boxRetriever: ComputationsBoxRetriever) = collectErrors(
    validateZeroOrPositiveInteger(this),
    cannotExistErrorIf(hasValue && boxRetriever.cpQ8().isTrue),
    mandatoryIfCompanyIsTrading(boxRetriever, "CP88", value),
    annualInvestmentAllowanceNotGreaterThanMaxAIA(boxRetriever)
  )

  private def annualInvestmentAllowanceNotGreaterThanMaxAIA(retriever: ComputationsBoxRetriever): Set[CtValidation] = {
    val expenditureQualifyingAnnualInvestmentAllowance: Int = retriever.cp83().orZero
    val aiaThreshold: Int = retriever.cato02().value

    val maxAIA = Math.min(expenditureQualifyingAnnualInvestmentAllowance, aiaThreshold)

    value match {
      case Some(aiaClaimed) if aiaClaimed > maxAIA =>
        Set(CtValidation(boxId = Some("CP88"), errorMessageKey = "error.CP88.annualInvestmentAllowanceExceeded", args = Some(Seq(maxAIA.toString))))
      case _ =>
        Set()
    }
  }
}

object CP88 {

  def apply(value: Int): CP88 = new CP88(Some(value))
}
