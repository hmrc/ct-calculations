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
import uk.gov.hmrc.ct.accounts.frs102.boxes.loansToDirectors.{AC306A, AC307A, AC308A, AC309A}
import uk.gov.hmrc.ct.box.CtTypeConverters

trait LoansToDirectorsCalculator extends CtTypeConverters {

  def calculateLoanBalanceAtEndOfPOA(ac306A: AC306A, ac307A: AC307A, ac308A: AC308A): AC309A = {
    (ac306A.value, ac307A.value, ac308A.value) match {
      case (None, None, None) => AC309A(None)
      case _ => AC309A(Some(ac306A.orZero + ac307A.orZero - ac308A.orZero))
    }
  }
}
