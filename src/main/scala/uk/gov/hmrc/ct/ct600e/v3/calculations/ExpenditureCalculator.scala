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

trait ExpenditureCalculator {

  def calculateTotalExpenditure(e95: E95, e100: E100, e105: E105, e110: E110, e115: E115, e120: E120): E125 = {
    val expenditureFields = Seq(e95, e100, e105, e110, e115, e120)
    if (expenditureFields.exists(_.value.nonEmpty)) {
      E125(Some(expenditureFields.map(_.orZero).sum))
    } else
      E125(None)
  }
}
