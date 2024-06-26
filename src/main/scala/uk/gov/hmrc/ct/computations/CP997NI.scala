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
import uk.gov.hmrc.ct.computations.nir.NorthernIrelandRateValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP997NI (value: Option[Int]) extends CP997Abstract(value)

object CP997NI extends NorthernIrelandRateValidation with Calculated[CP997NI, ComputationsBoxRetriever] {

  override def calculate(boxRetriever: ComputationsBoxRetriever): CP997NI = {
    if (boxRetriever.cato01().value > 0 && nirActiveForCurrentAccountingPeriod(boxRetriever)) {
      CP997NI(
        if (mayHaveNirLosses(boxRetriever)) Some(boxRetriever.cp997d().orZero + boxRetriever.cp997e().orZero)

        else boxRetriever.cp997d().value
      )
    } else CP997NI(None)
  }

}
