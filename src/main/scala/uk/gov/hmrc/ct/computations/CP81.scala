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

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.TotalExpenditureQualifyingForTheFirstYearAllowanceCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP81(value: Int) extends CtBoxIdentifier(name = "Total expenditure qualifying for the first year allowance (FYA)") with CtInteger

object CP81 extends Calculated[CP81, ComputationsBoxRetriever] with TotalExpenditureQualifyingForTheFirstYearAllowanceCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP81 = {
    totalExpenditureQualifyingForTheFirstYearAllowance(cp79 = fieldValueRetriever.retrieveCP79(),
                                                       cp80 = fieldValueRetriever.retrieveCP80())
  }

}
