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

import uk.gov.hmrc.ct.box.{Calculated, CtBoolean, CtBoxIdentifier}
import uk.gov.hmrc.ct.computations.calculations.LowEmissionCarsCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class LEC12(value: Boolean) extends CtBoxIdentifier("Disposals Exceed Main Rate Pool") with CtBoolean

object LEC12 extends Calculated[LEC12, ComputationsBoxRetriever] with LowEmissionCarsCalculator  {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): LEC12 =
    LEC12(disposalsExceedsMainRatePool(fieldValueRetriever.lec01(),
      fieldValueRetriever.cp78(),
      fieldValueRetriever.cp82(),
      fieldValueRetriever.cp672()
    ))
}
