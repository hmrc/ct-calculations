/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.Validators.ComputationValidatableBox
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP87Input(value: Option[Int]) extends CtBoxIdentifier(name = "First year allowance claimed")  with CtOptionalInteger with Input with ComputationValidatableBox[ComputationsBoxRetriever] with CtTypeConverters{
  override def validate(boxRetriever: ComputationsBoxRetriever) = {
    collectErrors(
      validateZeroOrPositiveInteger(this),
      cannotExistErrorIf(hasValue && boxRetriever.cpQ8().isTrue),
      mandatoryIfCompanyIsTrading(boxRetriever,"CP87Input", value),
      firstYearAllowanceNotGreaterThanMaxFYA(boxRetriever)
    )
  }

  private def firstYearAllowanceNotGreaterThanMaxFYA(retriever: ComputationsBoxRetriever): Set[CtValidation] = {

    val expenditureQualifyingForFirstYearAllowanceInput = retriever.cp81()
    val cpAux1 = retriever.cpAux1()

    val maxFYA = expenditureQualifyingForFirstYearAllowanceInput + cpAux1

    value match {
      case Some(fyaClaimed) if fyaClaimed > maxFYA =>
        Set(CtValidation(boxId = Some("CP87Input"), errorMessageKey = "error.CP87Input.firstYearAllowanceClaimExceedsAllowance", args = Some(Seq(maxFYA.toString))))
      case _ =>
        Set()
    }
  }
}

object CP87Input {

  def apply(int: Int): CP87Input = CP87Input(Some(int))

}
