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

package uk.gov.hmrc.ct.ct600e.validations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalString, CtValidation}

trait ValidateRegisteredCharityNumber {

  def validateRegisteredCharityNumber(box: CtOptionalString with CtBoxIdentifier): Set[CtValidation] = box.value match {
    case Some(v) if v.length < 6 || v.length > 8 || v.exists(!_.isDigit) => Set(CtValidation(Some(box.id), s"error.${box.id}.invalidRegNumber"))
    case _ => Set()
  }

}
