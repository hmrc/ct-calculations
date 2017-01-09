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

package uk.gov.hmrc.ct.accounts.frs102.calculations

import uk.gov.hmrc.ct.accounts.calculations.DebitAwareCalculation
import uk.gov.hmrc.ct.accounts.frs102.boxes._

trait TotalShareholdersFundsCalculator extends DebitAwareCalculation {

  def calculateCurrentTotalShareholdersFunds(ac70: AC70, ac76: AC76, ac74: AC74): AC80 = {
    sum(ac70, ac74, ac76)(AC80.apply)
  }

  def calculatePreviousTotalShareholdersFunds(ac71: AC71, ac77: AC77, ac75: AC75): AC81 = {
    sum(ac71, ac75, ac77)(AC81.apply)
  }
}
