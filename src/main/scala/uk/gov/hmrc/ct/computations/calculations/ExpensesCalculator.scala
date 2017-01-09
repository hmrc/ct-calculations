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

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._
import uk.gov.hmrc.ct.{CATO14, CATO15, CATO16}

trait ExpensesCalculator extends CtTypeConverters {

  def calculateTotalExpenses(cato14: CATO14, cato15: CATO15, cato16: CATO16): CP38 = {
    val result = cato14 + cato15 + cato16
    CP38(result)
  }

  def calculateDirectorsExpenses(cp15: CP15, cp16: CP16, cp17: CP17, cp18: CP18,
                                 cp19: CP19, cp20: CP20, cp21: CP21): CATO14 = {
    val result = cp15 + cp16 + cp17 + cp18 + cp19 + cp20 + cp21
    CATO14(result)
  }

  def calculatePropertyExpenses(cp22: CP22, cp23: CP23, cp24: CP24): CATO15 = {
    val result = cp22 + cp23 + cp24
    CATO15(result)
  }

  def calculateGeneralAdministrativeExpenses(cp25: CP25, cp26: CP26, cp27: CP27,
                                             cp28: CP28, cp29: CP29, cp30: CP30,
                                             cp31: CP31, cp32: CP32, cp33: CP33,
                                             cp34: CP34, cp35: CP35, cp36: CP36,
                                             cp37: CP37): CATO16 = {
    val result = cp25 + cp26 + cp27 + cp28 + cp29 + cp30 + cp31 + cp32 + cp33 + cp34 + cp35 + cp36 + cp37
    CATO16(result)
  }
}
