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

import uk.gov.hmrc.ct.accounts.frs102.boxes._
import uk.gov.hmrc.ct.box.CtTypeConverters

trait TotalShareholdersFundsCalculator extends CtTypeConverters {

  def calculateCurrentTotalShareholdersFunds(ac70: AC70, ac76: AC76, ac74: AC74): AC80 = {
    (ac70.value, ac76.value, ac74.value) match {
      case (None, None, None) => AC80(None)
      case _ => AC80(Some(ac70 + ac76 + ac74))
    }
  }

  def calculatePreviousTotalShareholdersFunds(ac71: AC71, ac77: AC77, ac75: AC75): AC81 = {
    (ac71.value, ac77.value, ac75.value) match {
      case (None, None, None) => AC81(None)
      case _ => AC81(Some(ac71 + ac77 + ac75))
    }
  }

}
