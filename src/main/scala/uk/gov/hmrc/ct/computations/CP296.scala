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

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.calculations.SBACalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.utils.DateImplicits._

case class CP296(value: Option[Int]) extends CtBoxIdentifier(name = "Total Structure and Building Allowance") with CtOptionalInteger

object CP296 extends Calculated[CP296, ComputationsBoxRetriever] with SBACalculator {

  def getCostForEachBuilding(boxRetriever: ComputationsBoxRetriever): List[Option[Int]] = {
    boxRetriever.sba01().values.filter(building => {
        building.nonResidentialActivityStart match {
          case None => false
          case Some(date) => date >= boxRetriever.cp1().value
        }
      }
    ).map(building => building.cost)
  }
  
  override def calculate(boxRetriever: ComputationsBoxRetriever): CP296 = {
    CP296(sumAmount(getCostForEachBuilding(boxRetriever)))
  }
}