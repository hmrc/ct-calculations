/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP3010(value: Option[Int]) extends CtBoxIdentifier(name = "Donations to governing sport bodies")
  with CtOptionalInteger with Input with ValidatableBox[ComputationsBoxRetriever] {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      requiredErrorIf(boxRetriever.cpQ321().isTrue && !hasValue),
      validateZeroOrPositiveInteger(this),
      cannotExistErrorIf(hasValue && boxRetriever.cpQ321().isFalse)
    )
  }
}


object CP3010 {

  def apply(int: Int): CP3010 = CP3010(Some(int))

}
