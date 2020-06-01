/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP984(value : Int) extends CtBoxIdentifier("Net turnover off payroll working") with CtInteger {

}

object CP984 extends Calculated[CP984, ComputationsBoxRetriever]{
  override def calculate(boxRetriever: ComputationsBoxRetriever): CP984 = {
    CP984(boxRetriever.cp983().value.getOrElse(0) - boxRetriever.cp981().value.getOrElse(0))
  }
}
