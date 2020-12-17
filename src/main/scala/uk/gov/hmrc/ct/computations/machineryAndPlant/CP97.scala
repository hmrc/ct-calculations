/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.machineryAndPlant

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger, CtTypeConverters}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP97(value: Int) extends CtBoxIdentifier(name = "Total first year allowance not claimed") with CtInteger {}

  object CP97 extends Calculated[CP97, ComputationsBoxRetriever] with CtTypeConverters {
    override def calculate(boxRetriever: ComputationsBoxRetriever): CP97 = {
      val totalFYAClaimed = boxRetriever.cp87()
      val totalExpenditureForFya = boxRetriever.cp94()

      CP97(totalExpenditureForFya - totalFYAClaimed)
    }
}


