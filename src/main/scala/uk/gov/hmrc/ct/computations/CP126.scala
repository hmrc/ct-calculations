/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP126(value: Int) extends CtBoxIdentifier(name = "Coronavirus support schemes overpayment now due") with CtInteger

object CP126 extends Calculated[CP126, ComputationsBoxRetriever] {
  override def calculate(boxRetriever: ComputationsBoxRetriever): CP126 = {
    val cp122 = boxRetriever.cp122()
    val cp123 = boxRetriever.cp123()
    val cp124 = boxRetriever.cp124()
    val cp125 = boxRetriever.cp125()

    CP126(cp122.value.getOrElse(0) - cp123.value.getOrElse(0) - cp124.value.getOrElse(0) + cp125.value.getOrElse(0))
  }
}