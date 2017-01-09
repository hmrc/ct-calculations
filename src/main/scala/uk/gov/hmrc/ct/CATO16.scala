/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
