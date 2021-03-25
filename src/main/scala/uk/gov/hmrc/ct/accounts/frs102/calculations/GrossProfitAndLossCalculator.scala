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

package uk.gov.hmrc.ct.accounts.frs102.calculations

import uk.gov.hmrc.ct.accounts.calculations.DebitAwareCalculation
import uk.gov.hmrc.ct.accounts.frs10x.boxes.AC24
import uk.gov.hmrc.ct.accounts.frs10x.boxes.{AC13, AC15, AC16, AC17, AC25}
import uk.gov.hmrc.ct.accounts.{AC12, AC14, AC401, AC402, AC403, AC404}

trait GrossProfitAndLossCalculator  extends DebitAwareCalculation {
  def calculateAC16(ac12: AC12, ac24: AC24, ac401: AC401, ac403: AC403, ac14: AC14): AC16 = {
    sum(ac12, ac24, ac401, ac403, ac14)(AC16.apply)
  }

  def calculateAC17(ac13: AC13, ac25: AC25, ac402: AC402, ac404: AC404, ac15: AC15): AC17 = {
    sum(ac13, ac25, ac402, ac404, ac15)(AC17.apply)
  }
}