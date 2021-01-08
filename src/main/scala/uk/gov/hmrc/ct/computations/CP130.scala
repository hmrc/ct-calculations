/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP130(value: Int) extends CtBoxIdentifier(name = "Total income from coronavirus (COVID-19) business support grants") with CtInteger

object CP130 extends Calculated[CP130, ComputationsBoxRetriever] {
  override def calculate(boxRetriever: ComputationsBoxRetriever): CP130 = {
    val cp122 = boxRetriever.cp122()
    val cp127 = boxRetriever.cp127()

    CP130(cp122.value.getOrElse(0) + cp127.value.getOrElse(0))
  }
}
