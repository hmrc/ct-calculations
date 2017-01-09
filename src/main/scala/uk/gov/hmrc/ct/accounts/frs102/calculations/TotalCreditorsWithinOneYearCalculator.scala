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

trait TotalCreditorsWithinOneYearCalculator extends DebitAwareCalculation {

  def calculateCurrentTotalCreditorsWithinOneYear(ac142: AC142, ac144: AC144, ac146: AC146, ac148: AC148, ac150: AC150, ac152: AC152): AC154 = {
    sum(ac142, ac144, ac146, ac148, ac150, ac152)(AC154.apply)
  }

  def calculatePreviousTotalCreditorsWithinOneYear(ac143: AC143, ac145: AC145, ac147: AC147, ac149: AC149, ac151: AC151, ac153: AC153): AC155 = {
    sum(ac143, ac145, ac147, ac149, ac151, ac153)(AC155.apply)
  }
}
