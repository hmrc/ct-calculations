/*
 * Copyright 2020 HM Revenue & Customs
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

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtOptionalInteger, CtValidation, EndDate}

package object offPayRollWorking {
  val opwApplies2020 = new LocalDate("2017-04-05")

  def isOPWEnabled(apEndDate: EndDate) = apEndDate.value.isAfter(opwApplies2020)

  def DeductionCannotBeGreaterThanProfit(profit: CtOptionalInteger, loss: CtOptionalInteger): Set[CtValidation] ={

    (loss.value, profit.value) match {
      case (Some(lossValue),Some(profitValue)) if lossValue > profitValue => Set(CtValidation(Some(loss.getClass.getSimpleName), s"error.${loss.getClass.getSimpleName}.exceeds.${profit.getClass.getSimpleName}"))
      case (_ , _) => Set.empty
    }
  }
}
