/*
 * Copyright 2024 HM Revenue & Customs
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

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.SBACalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP298(value: Option[Int]) extends CtBoxIdentifier("Total Relief Claimable") with CtOptionalInteger

object CP298 extends Calculated[CP298, ComputationsBoxRetriever] with SBACalculator {

  def getTotalRelief(computationsBoxRetriever: ComputationsBoxRetriever): List[Option[Int]] = {
    computationsBoxRetriever.sba01().values.map(building =>

      getSbaDetails(computationsBoxRetriever.cp1().value, computationsBoxRetriever.cp2().value, building.earliestWrittenContract, building.cost).flatMap(_.totalCost)
    )
  }

  override def calculate(boxRetriever: ComputationsBoxRetriever): CP298 = CP298(sumAmount(getTotalRelief(boxRetriever)))
}
