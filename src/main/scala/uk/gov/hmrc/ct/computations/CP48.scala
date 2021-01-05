/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP48(value: Option[Int]) extends CtBoxIdentifier(name = "Donations") with CtOptionalInteger with Input with ValidatableBox[ComputationsBoxRetriever] {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateZeroOrPositiveInteger(this),
      failIf(boxRetriever.cp29().value != this.value) {
        Set(CtValidation(Some("CP48"), "error.CP48.must.equal.CP29"))
      }
    )
  }

}

object CP48 {

  def apply(int: Int): CP48 = CP48(Some(int))

}
