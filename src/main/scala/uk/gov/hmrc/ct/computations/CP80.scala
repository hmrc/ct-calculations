/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input, ValidatableBox}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP80(value: Option[Int]) extends CtBoxIdentifier(name = "Other (FYA) expenditure") with CtOptionalInteger with Input with ValidatableBox[ComputationsBoxRetriever] {

override def validate(boxRetriever: ComputationsBoxRetriever) = {
    validateZeroOrPositiveInteger(this)
  }
}

object CP80 {

  def apply(value: Int): CP80 = CP80(Some(value))
}
