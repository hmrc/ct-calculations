/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.IncomeFromPropertyCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP511(value: Int) extends CtBoxIdentifier(name = "Total Income from property") with CtInteger

object CP511 extends Calculated[CP511, ComputationsBoxRetriever] with IncomeFromPropertyCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP511 = {
    totalIncomeFromProperty(cp509 = fieldValueRetriever.cp509(),
                            cp510 = fieldValueRetriever.cp510())
  }
}
