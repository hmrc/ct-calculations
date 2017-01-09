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

package uk.gov.hmrc.ct.accounts.frs105.calculations

import uk.gov.hmrc.ct.accounts.calculations.DebitAwareCalculation
import uk.gov.hmrc.ct.accounts.frs105.boxes._

trait TotalAssetsLessCurrentLiabilitiesCalculator extends DebitAwareCalculation {

  def calculateCurrentTotalAssetsLessCurrentLiabilities(ac60: AC60, ac450: AC450, ac460: AC460): AC62 = {
    sum(ac60, ac450, ac460)(AC62.apply)
  }

  def calculatePreviousTotalAssetsLessCurrentLiabilities(ac61: AC61, ac451: AC451, ac461: AC461): AC63 = {
    sum(ac61, ac451, ac461)(AC63.apply)
  }

}
