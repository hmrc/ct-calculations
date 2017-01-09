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

trait TotalCurrentAssetsCalculator extends CtTypeConverters {

  def calculateCurrentTotalCurrentAssets(ac50: AC50, ac52: AC52, ac54: AC54): AC56 = {
    (ac50.value, ac52.value, ac54.value) match {
      case (None, None, None) => AC56(None)
      case _ => AC56(Some(ac50 + ac52 + ac54))
    }
  }

  def calculatePreviousTotalCurrentAssets(ac51: AC51, ac53: AC53, ac55: AC55): AC57 = {
    (ac51.value, ac53.value, ac55.value) match {
      case (None, None, None) => AC57(None)
      case _ => AC57(Some(ac51 + ac53 + ac55))
    }
  }

}
