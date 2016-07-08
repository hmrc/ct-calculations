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

trait ProfitOrLossBeforeTaxCalculator {

  def calculateAC32(aC26: AC26, aC28: AC28, aC30: AC30): AC32 = {
    AC32(aC26.value + aC28.orZero - aC30.orZero)
  }

  def calculateAC33(aC27: AC27, aC29: AC29, aC31: AC31): AC33 = {
    AC33(aC27.value + aC29.orZero - aC31.orZero)
  }
}
