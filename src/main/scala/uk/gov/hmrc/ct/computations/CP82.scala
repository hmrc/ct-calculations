/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP82(value: Option[Int]) extends CtBoxIdentifier(name = "Additions qualifying for writing down allowance") with CtOptionalInteger with Input with SelfValidatableBox[ComputationsBoxRetriever, Option[Int]] {
  override def validate(boxRetriever: ComputationsBoxRetriever) = {
    collectErrors(
      cannotExistErrorIf(hasValue && boxRetriever.cpQ8().isTrue),
      validateZeroOrPositiveInteger()
    )
  }
}
object CP82 {

  def apply(value: Int): CP82 = CP82(Some(value))
}
