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

trait TotalProfitsBeforeDeductionsCalculator extends CtTypeConverters {

  def computeTotalProfitsBeforeDeductionsAndReliefs(cp284: CP284,
                                                    cp58: CP58,
                                                    cp511: CP511,
                                                    cp502: CP502): CP293 = {
    val result = (cp284.orZero max 0) + cp58.value + cp511.value + cp502.orZero
    CP293(result max 0)
  }
}
