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

package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoolean, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.v3.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever

case class LPQ01(value: Boolean) extends CtBoxIdentifier(name = "Declare loans to participators") with CtBoolean

object LPQ01 extends Calculated[LPQ01, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(boxRetriever: CT600ABoxRetriever): LPQ01 = {
    calculateLPQ01(
      boxRetriever.lpq04(),
      boxRetriever.lpq10(),
      boxRetriever.a5(),
      boxRetriever.lpq03()
    )
  }

}
