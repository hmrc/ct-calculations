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

case class CATO14(value: Int) extends CtBoxIdentifier(name = "Directors Expenses") with CtInteger with NotInPdf

object CATO14 extends Calculated[CATO14, ComputationsBoxRetriever] with ExpensesCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CATO14 = {
    calculateDirectorsExpenses(cp15 = fieldValueRetriever.cp15(),
                               cp16 = fieldValueRetriever.cp16(),
                               cp17 = fieldValueRetriever.cp17(),
                               cp18 = fieldValueRetriever.cp18(),
                               cp19 = fieldValueRetriever.cp19(),
                               cp20 = fieldValueRetriever.cp20(),
                               cp21 = fieldValueRetriever.cp21())
  }
}
