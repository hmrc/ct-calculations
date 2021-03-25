/*
 * Copyright 2021 HM Revenue & Customs
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

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever


case class CATO23(value: Int) extends CtBoxIdentifier(name = "Net non-trading income") with CtInteger

object CATO23 extends Calculated[CATO23, ComputationsBoxRetriever] with NonTradeIncomeCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CATO23 = {

    NetNonTradeIncomeCalculation(
      cato01 = fieldValueRetriever.cato01(),
      cp997NI = fieldValueRetriever.cp997NI(),
      cp998 = fieldValueRetriever.cp998()
    )
  }
}
