/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP8(value: Option[Int]) extends CtBoxIdentifier(name = "Cost Of Sales")
  with CtOptionalInteger
  with Input
  with ValidatableBox[ComputationsBoxRetriever] {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      failIf(value.nonEmpty){
        validateMoney(value)
      },
      failIf(value.isEmpty){
        validateAsMandatory(this)
      },
      validateZeroOrPositiveInteger(this)
    )
  }
}

object CP8 {

  def apply(int: Int): CP8 = CP8(Some(int))
}
