/*
 * Copyright 2016 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs10x.abridged.calculations

import uk.gov.hmrc.ct.accounts.frs10x.abridged._
import uk.gov.hmrc.ct.box.CtTypeConverters

trait NetCurrentAssetsLiabilitiesCalculator extends CtTypeConverters {

  def calculateCurrentNetCurrentAssetsLiabilities(ac56: AC56, ac1076: AC1076, ac58: AC58): AC60 = {
    (ac56.value, ac1076.value, ac58.value) match {
      case (None, None, None) => AC60(None)
      case _ => AC60(Some(ac56 + ac1076 - ac58))
    }
  }

  def calculatePreviousNetCurrentAssetsLiabilities(ac57: AC57, ac1077: AC1077, ac59: AC59): AC61 = {
    (ac57.value, ac1077.value, ac59.value) match {
      case (None, None, None) => AC61(None)
      case _ => AC61(Some(ac57 + ac1077 - ac59))
    }
  }

}
