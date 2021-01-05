/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP49(value: Option[Int]) extends CtBoxIdentifier(name = "Legal and professional fees") with CtOptionalInteger with Input with ValidatableBox[ComputationsBoxRetriever] {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = validateZeroOrPositiveInteger(this)

}

object CP49 {

  def apply(int: Int): CP49 = CP49(Some(int))

}
