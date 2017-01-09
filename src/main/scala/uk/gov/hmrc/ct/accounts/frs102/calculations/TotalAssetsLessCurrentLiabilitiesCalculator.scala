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

import uk.gov.hmrc.ct.accounts.frs102.boxes._
import uk.gov.hmrc.ct.box.CtTypeConverters

trait TotalAssetsLessCurrentLiabilitiesCalculator extends CtTypeConverters {

  def calculateCurrentTotalAssetsLessCurrentLiabilities(ac60: AC60, ac48: AC48): AC62 = {
    (ac60.value, ac48.value) match {
      case (None, None) => AC62(None)
      case _ => AC62(Some(ac60 + ac48))
    }
  }

  def calculatePreviousTotalAssetsLessCurrentLiabilities(ac61: AC61, ac49: AC49): AC63 = {
    (ac61.value, ac49.value) match {
      case (None, None) => AC63(None)
      case _ => AC63(Some(ac61 + ac49))
    }
  }

}
