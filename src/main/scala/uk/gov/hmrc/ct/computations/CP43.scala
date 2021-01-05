/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input, ValidatableBox}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP43(value: Option[Int]) extends CtBoxIdentifier(name = "Interest Received") with CtOptionalInteger with Input with ValidatableBox[ComputationsBoxRetriever] {

  override def validate(boxRetriever: ComputationsBoxRetriever) = {
      validateIntegerRange(boxId, this, 0, 99999999)
  }
}

object CP43 {
  def apply(int: Int): CP43 = CP43(Some(int))
}
