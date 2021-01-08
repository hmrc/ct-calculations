/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger, NotInPdf}
import uk.gov.hmrc.ct.computations.calculations.ExpensesCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CATO16(value: Int) extends CtBoxIdentifier(name = "General Administrative Expenses") with CtInteger with NotInPdf

object CATO16 extends Calculated[CATO16, ComputationsBoxRetriever] with ExpensesCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CATO16 = {
    calculateGeneralAdministrativeExpenses(cp25 = fieldValueRetriever.cp25(),
                                           cp26 = fieldValueRetriever.cp26(),
                                           cp27 = fieldValueRetriever.cp27(),
                                           cp28 = fieldValueRetriever.cp28(),
                                           cp29 = fieldValueRetriever.cp29(),
                                           cp30 = fieldValueRetriever.cp30(),
                                           cp31 = fieldValueRetriever.cp31(),
                                           cp32 = fieldValueRetriever.cp32(),
                                           cp33 = fieldValueRetriever.cp33(),
                                           cp34 = fieldValueRetriever.cp34(),
                                           cp35 = fieldValueRetriever.cp35(),
                                           cp36 = fieldValueRetriever.cp36(),
                                           cp37 = fieldValueRetriever.cp37())
  }
}
