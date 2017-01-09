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

trait TotalFixedAssetsCalculator extends CtTypeConverters {

  def calculateCurrentTotalFixedAssets(ac42: AC42, ac44: AC44): AC48 = {
    (ac42.value, ac44.value) match {
      case (None, None) => AC48(None)
      case _ => AC48(Some(ac42 + ac44))
    }
  }

  def calculatePreviousTotalFixedAssets(ac43: AC43, ac45: AC45): AC49 = {
    (ac43.value, ac45.value) match {
      case (None, None) => AC49(None)
      case _ => AC49(Some(ac43 + ac45))
    }
  }

}
