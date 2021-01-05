/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP3030(value: Option[Int]) extends CtBoxIdentifier(name = "Non-qualifying donations to grassroots sports clubs")
  with CtOptionalInteger with Input with ValidatableBox[ComputationsBoxRetriever] {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      requiredErrorIf(boxRetriever.cpQ321().isTrue && !hasValue),
      validateZeroOrPositiveInteger(this),
      cannotExistErrorIf(hasValue && boxRetriever.cpQ321().isFalse)
    )
  }
}


object CP3030 {

  def apply(int: Int): CP3030 = CP3030(Some(int))

}
