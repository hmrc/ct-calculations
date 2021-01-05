/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

case class E170(value: Option[Int]) extends CtBoxIdentifier("Held at the end of the period (use accounts figures): Loans and non-trade debtors") with CtOptionalInteger

object E170 extends Calculated[E170, CT600EBoxRetriever] with CtTypeConverters {
  override def calculate(boxRetriever: CT600EBoxRetriever) : E170 = {
    val e170a = boxRetriever.e170A()
    val e170b = boxRetriever.e170B()

    E170(
      (e170a.value, e170b.value) match {
        case (None, None)  => None
        case _ => Some(e170a plus e170b)
      }
    )
  }
}
