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

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.calculations.TotalExpenditureQualifyingForTheFirstYearAllowanceCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.v2.B108

case class CP81(value: Int) extends CtBoxIdentifier(name = "Total expenditure qualifying for the first year allowance (FYA)") with CtInteger

object CP81 extends Linked[CP81Input, CP81] with CtTypeConverters {

  override def apply(cp81Input: CP81Input): CP81 = new CP81(cp81Input)

}
