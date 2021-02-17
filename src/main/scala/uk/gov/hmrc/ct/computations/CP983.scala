/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDormancyBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.validation.TurnoverValidation

case class CP983(value: Option[Int]) extends CtBoxIdentifier(name = "Turnover from off-payroll working")
  with CtOptionalInteger
  with Input
  with ValidatableBox[ComputationsBoxRetriever with Frs10xDormancyBoxRetriever]
  with TurnoverValidation {

  val compsStartDate = { br: ComputationsBoxRetriever => br.cp1() }
  val compsEndDate = { br: ComputationsBoxRetriever => br.cp2() }

  override def validate(boxRetriever: ComputationsBoxRetriever with Frs10xDormancyBoxRetriever): Set[CtValidation] = {
    val cato24 = boxRetriever.cato24
    val dormant = boxRetriever.acq8999().orFalse
    collectErrors(
      requiredErrorIf(cato24.isTrue && !dormant &&  this.value.isEmpty),
      validateZeroOrPositiveInteger(this),
      validateHmrcTurnover(boxRetriever, compsStartDate, compsEndDate, errorSuffix = "", secondaryIncome = boxRetriever.cp7().orZero)
    )

  }
}

object CP983 {
  def apply(value: Int): CP983 = CP983(Some(value))
}
