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

trait NetCurrentAssetsLiabilitiesCalculator extends DebitAwareCalculation {

  def calculateCurrentNetCurrentAssetsLiabilities(ac455: AC455, ac465: AC465, ac58: AC58): AC60 = {
    sum(ac455, ac465, ac58)(AC60.apply)
  }

  def calculatePreviousNetCurrentAssetsLiabilities(ac456: AC456, ac466: AC466, ac59: AC59): AC61 = {
    sum(ac456, ac466, ac59)(AC61.apply)
  }

}
