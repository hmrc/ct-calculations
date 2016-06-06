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

package uk.gov.hmrc.ct.box.validation

import uk.gov.hmrc.ct.box.{CtOptionalInteger, CtValidation}
import uk.gov.hmrc.ct.domain.ValidationConstants._

trait OptionalMoneyValidation {

  self: CtOptionalInteger =>

  protected def validateMoneyRange(boxId: String, min: Int = MIN_MONEY_AMOUNT_ALLOWED, max: Int = MAX_MONEY_AMOUNT_ALLOWED): Set[CtValidation] = {
    value match {
      case Some(money) if money < min => Set(CtValidation(Some(boxId), s"error.$boxId.below.min"))
      case Some(money) if money > max => Set(CtValidation(Some(boxId), s"error.$boxId.exceeds.max"))
      case _ => Set.empty
    }
  }
}
