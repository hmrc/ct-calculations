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

package uk.gov.hmrc.ct.accounts.frs102.calculations

import uk.gov.hmrc.ct.accounts.calculations.DebitAwareCalculation
import uk.gov.hmrc.ct.accounts.frs102.boxes._
import uk.gov.hmrc.ct.box.CtTypeConverters

trait BalanceSheetCreditorsCalculator extends DebitAwareCalculation  {

  def calculateAC162(ac156: AC156, ac158: AC158, ac160: AC160): AC162 = {
    (ac156.value, ac158.value, ac160.value) match {
      case (None, None, None) => AC162(None)
      case _ => sum(ac156, ac158, ac160)(AC162.apply)
    }
  }

  def calculateAC163(ac157: AC157, ac159: AC159, ac161: AC161): AC163 = {
    (ac157.value, ac159.value, ac161.value) match {
      case (None, None, None) => AC163(None)
      case _ => sum(ac157, ac159, ac161)(AC163.apply)
    }
  }

}
