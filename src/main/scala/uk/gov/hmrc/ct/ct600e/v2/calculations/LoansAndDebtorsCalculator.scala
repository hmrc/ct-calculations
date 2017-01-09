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

package uk.gov.hmrc.ct.ct600e.v2.calculations

import uk.gov.hmrc.ct.ct600e.v2.{E24e, E24eA, E24eB}

trait LoansAndDebtorsCalculator {
  def calculateFieldValue(e24eA: E24eA, e24eB: E24eB): E24e = {
    val fields = Seq(e24eA, e24eB)

    if (fields.exists(_.hasValue))
      E24e(Some(fields.map(_.orZero).sum))
    else
      E24e(None)
  }
}
