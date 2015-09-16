/*
 * Copyright 2015 HM Revenue & Customs
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
    calculateGeneralAdministrativeExpenses(cp25 = fieldValueRetriever.retrieveCP25(),
                                           cp26 = fieldValueRetriever.retrieveCP26(),
                                           cp27 = fieldValueRetriever.retrieveCP27(),
                                           cp28 = fieldValueRetriever.retrieveCP28(),
                                           cp29 = fieldValueRetriever.retrieveCP29(),
                                           cp30 = fieldValueRetriever.retrieveCP30(),
                                           cp31 = fieldValueRetriever.retrieveCP31(),
                                           cp32 = fieldValueRetriever.retrieveCP32(),
                                           cp33 = fieldValueRetriever.retrieveCP33(),
                                           cp34 = fieldValueRetriever.retrieveCP34(),
                                           cp35 = fieldValueRetriever.retrieveCP35(),
                                           cp36 = fieldValueRetriever.retrieveCP36(),
                                           cp37 = fieldValueRetriever.retrieveCP37())
  }
}
