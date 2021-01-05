/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP263(value: Option[Int]) extends CtBoxIdentifier("Post reform losses brought forward and deducted") with CtOptionalInteger

object CP263 extends Calculated[CP263, ComputationsBoxRetriever] with CtTypeConverters {
  override def calculate(boxRetriever: ComputationsBoxRetriever): CP263 = {
    CP263(
      (boxRetriever.cp283b().value, boxRetriever.chooseCp997().value) match {
        case (None, None) => None
        case _ => Some(boxRetriever.cp283b() + boxRetriever.chooseCp997())
      }
    )
  }
}
