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

package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.box.{CtValidation, CtValue, ValidatableBox}

trait AccountsMoneyValidation {

  self: CtValue[Option[Int]] =>

  def validateMoney(boxId: String): Set[CtValidation] = {
    value match {
      case Some(x) if x < -99999999 => Set(CtValidation(boxId = Some(boxId), s"error.$boxId.below.min"))
      case Some(x) if x > 99999999 => Set(CtValidation(boxId = Some(boxId), s"error.$boxId.above.max"))
      case _ => Set.empty
    }
  }
}
