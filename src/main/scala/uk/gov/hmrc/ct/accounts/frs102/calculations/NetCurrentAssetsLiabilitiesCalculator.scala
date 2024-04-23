/*
 * Copyright 2024 HM Revenue & Customs
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

trait NetCurrentAssetsLiabilitiesCalculator extends CtTypeConverters {

  def calculateCurrentNetCurrentAssetsLiabilities(ac56: AC56, ac138B: AC138B, ac58: AC58): AC60 = {
    (ac56.value, ac138B.value, ac58.value) match {
      case (None, None, None) => AC60(None)
      case _ => AC60(Some(ac56 + ac138B - ac58))
    }
  }

  def calculatePreviousNetCurrentAssetsLiabilities(ac57: AC57, ac139B: AC139B, ac59: AC59): AC61 = {
    (ac57.value, ac139B.value, ac59.value) match {
      case (None, None, None) => AC61(None)
      case _ => AC61(Some(ac57 + ac139B - ac59))
    }
  }

}
