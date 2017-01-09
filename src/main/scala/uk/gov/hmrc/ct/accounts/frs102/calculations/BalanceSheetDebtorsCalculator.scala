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

import uk.gov.hmrc.ct.accounts.calculations.DebitAwareCalculation
import uk.gov.hmrc.ct.accounts.frs102.boxes._

trait BalanceSheetDebtorsCalculator extends DebitAwareCalculation  {

  def calculateAC140(ac134: AC134, ac136: AC136, ac138: AC138): AC140 = {
    sum(ac134, ac136, ac138)(AC140.apply)
  }

  def calculateAC141(ac135: AC135, ac137: AC137, ac139: AC139): AC141 = {
    sum(ac135, ac137, ac139)(AC141.apply)
  }

}
