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

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.SBACalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP297(value: Option[Int]) extends CtBoxIdentifier("Total Structure and Building Allowance Claimed") with CtOptionalInteger

object CP297 extends Calculated[CP297, ComputationsBoxRetriever] with SBACalculator {
  override def calculate(boxRetriever: ComputationsBoxRetriever): CP297 = {
    val result  = boxRetriever.sba01().buildings.map {
      building => building.claims
    }

    CP297(sumAmount(result))

  }
}



