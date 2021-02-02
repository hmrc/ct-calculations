/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.validation.TurnoverValidation

case class CP983(value: Option[Int]) extends CtBoxIdentifier(name = "Turnover from off-payroll working")
  with CtOptionalInteger
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with TurnoverValidation {

  val compsStartDate = { br: ComputationsBoxRetriever => br.cp1() }
  val compsEndDate = { br: ComputationsBoxRetriever => br.cp2() }

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    val cato24 = boxRetriever.cato24
    collectErrors(
      requiredErrorIf(cato24.isTrue && this.value.isEmpty),
      validateZeroOrPositiveInteger(this),
      validateHmrcTurnover(boxRetriever, compsStartDate, compsEndDate, errorSuffix = "", secondaryIncome = boxRetriever.cp7().orZero,minimumAmount = false)
    )

  }
}

object CP983 {
  def apply(value: Int): CP983 = CP983(Some(value))
}
