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
import uk.gov.hmrc.ct.computations.{CP501, CP502}

trait NonBankInterestSimilarIncomeReceivableCalculator extends CtTypeConverters {

  def nonBankInterestSimilarIncomeReceivableCalculation(cp501: CP501,
                                                        cp502: CP502): Option[Int] = {
    val result = (cp501.value, cp502.value) match {
      case (Some(gross), Some(income)) =>
        Some(gross + income)
      case _ =>
        None
    }
    result
  }
}
