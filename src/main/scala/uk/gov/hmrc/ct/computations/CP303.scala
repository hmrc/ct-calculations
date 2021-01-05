/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP303(value: Option[Int]) extends CtBoxIdentifier(name = "Non qualifying charitable donations")
  with CtOptionalInteger with Input with ValidatableBox[ComputationsBoxRetriever] {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      cannotExistErrorIf(hasValue && boxRetriever.cpQ21().isFalse),
      requiredErrorIf(!hasValue && boxRetriever.cpQ21().isTrue),
      validateZeroOrPositiveInteger(this)
    )
  }
}

object CP303 {

  def apply(int: Int): CP303 = CP303(Some(int))

}
