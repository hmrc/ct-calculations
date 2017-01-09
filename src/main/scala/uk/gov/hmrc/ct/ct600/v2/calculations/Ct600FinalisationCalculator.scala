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

package uk.gov.hmrc.ct.ct600.v2.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.ct600.v2._

trait Ct600FinalisationCalculator extends CtTypeConverters {

  def computeTaxPayable(b70: B70, b79: B79, b84: B84): B86 = {
    val profitsChargeable = b70.value + b79.value.getOrElse(0)
    val incomeTaxDeductedFromGrossIncome = b84.value

    if (incomeTaxDeductedFromGrossIncome >= profitsChargeable) {
      B86(BigDecimal("0.00"))
    } else {
      B86(profitsChargeable - incomeTaxDeductedFromGrossIncome)
    }
  }

  def computeTaxRepayable(b70: B70, b79: B79, b84: B84): B85 = {
    val profitsChargeable = b70.value + b79.value.getOrElse(0)
    val incomeTaxDeductedFromGrossIncome = b84.value

    if (incomeTaxDeductedFromGrossIncome >= profitsChargeable) {
      B85(incomeTaxDeductedFromGrossIncome - profitsChargeable)
    } else {
      B85(BigDecimal("0.00"))
    }
  }
}
