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

package uk.gov.hmrc.ct.ct600e.v3.calculations

import uk.gov.hmrc.ct.ct600e.v3._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever._

trait IncomeCalculator {

  def calculateTotalIncome(e50: E50, e55: E55, e60: E60, e65: E65, e70: E70, e75: E75, e80: E80, e85: E85): E90 = {
    val incomeFields = Seq(e50, e55, e60, e65, e70, e75, e80, e85)

    if (anyHaveValue(incomeFields:_ *))
      E90(Some(incomeFields.map(_.orZero).sum))
    else E90(None)
  }
}
