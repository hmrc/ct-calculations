/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.validation.TurnoverValidation

case class CP7(inputValue: Option[Int], defaultValue: Option[Int]) extends CtBoxIdentifier(name = "Turnover/Sales")
  with CtOptionalInteger
  with InputWithDefault[Int]
  with ValidatableBox[ComputationsBoxRetriever]
  with TurnoverValidation {

  val compsStartDate = { br: ComputationsBoxRetriever => br.cp1() }

  val compsEndDate = { br: ComputationsBoxRetriever => br.cp2() }

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateHmrcTurnover(boxRetriever, compsStartDate, compsEndDate, errorSuffix = ""),
      validateAsMandatory(this)
    )
  }
}

object CP7 extends Linked[AP2, CP7] {

  def apply(inputValue: Option[Int]): CP7 = CP7(inputValue = inputValue, defaultValue = None)

  override def apply(source: AP2): CP7 = CP7(None, source.value)
}
