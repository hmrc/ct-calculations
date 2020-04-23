/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP985(value: Int) extends CtBoxIdentifier("Adjustments off payroll working retained profits") with CtInteger

object CP985 extends Calculated[CP985, ComputationsBoxRetriever] {

  override def calculate(boxRetriever: ComputationsBoxRetriever): CP985 = {
    CP985(
      boxRetriever.cp983.value - (boxRetriever.cp981.value.getOrElse(0) -  boxRetriever.cp980.value.getOrElse(0))
    )
  }
}
