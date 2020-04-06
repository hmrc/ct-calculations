/*
 * Copyright 2020 HM Revenue & Customs
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

case class CATO15(value: Int) extends CtBoxIdentifier(name = "Property Expenses") with CtInteger with NotInPdf

object CATO15 extends Calculated[CATO15, ComputationsBoxRetriever] with ExpensesCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CATO15 = {
    calculatePropertyExpenses(cp22 = fieldValueRetriever.cp22(),
                              cp23 = fieldValueRetriever.cp23(),
                              cp24 = fieldValueRetriever.cp24())
  }
}
