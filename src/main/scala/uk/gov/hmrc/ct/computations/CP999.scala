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

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.TotalDonationsCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP999(value: Int) extends CtBoxIdentifier(name = "Total Donations") with CtInteger

object CP999 extends Calculated[CP999, ComputationsBoxRetriever] with TotalDonationsCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP999 = {
    calculateTotalDonations(cpq21 = fieldValueRetriever.cpQ21(),
                            cp301 = fieldValueRetriever.cp301(),
                            cp302 = fieldValueRetriever.cp302())
  }
}
