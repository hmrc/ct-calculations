/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, CtValidation, Input, ValidatableBox}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP984(value: Option[Int]) extends CtBoxIdentifier(name = "Turnover from off-payroll working") with CtOptionalInteger with Input with ValidatableBox[ComputationsBoxRetriever]   {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = validateZeroOrPositiveInteger(this)
}

object CP984 {
  def apply(int: Int): CP984 = CP984(Some(int))
}