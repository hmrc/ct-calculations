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

package uk.gov.hmrc.ct.accounts.frs102.abridged.calculations

import uk.gov.hmrc.ct.accounts.frs102.abridged._
import uk.gov.hmrc.ct.box.CtTypeConverters

trait TotalNetAssetsLiabilitiesCalculator extends CtTypeConverters {

  def calculateCurrentTotalNetAssetsLiabilities(ac62: AC62, ac64: AC64, ac66: AC66, ac470: AC470): AC68 = {
    (ac62.value, ac64.value, ac66.value, ac470.value) match {
      case (None, None, None, None) => AC68(None)
      case _ => AC68(Some(ac62 - ac64 - ac66 - ac470))
    }
  }

  def calculatePreviousTotalNetAssetsLiabilities(ac63: AC63, ac65: AC65, ac67: AC67, ac471: AC471): AC69 = {
    (ac63.value, ac65.value, ac67.value, ac471.value) match {
      case (None, None, None, None) => AC69(None)
      case _ => AC69(Some(ac63 - ac65 - ac67 - ac471))
    }
  }
}
