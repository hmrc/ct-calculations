/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.QualifyingExpenditureOnMachineryCalculation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP253(value: Int) extends CtBoxIdentifier(name = "Qualifying expenditure on machinery and plant on other assets") with CtInteger

object CP253 extends Calculated[CP253, ComputationsBoxRetriever] with QualifyingExpenditureOnMachineryCalculation {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP253 = {
    qualifyingExpenditureCalculation(fieldValueRetriever.cp82(),
                                     fieldValueRetriever.cp83())
  }

}
