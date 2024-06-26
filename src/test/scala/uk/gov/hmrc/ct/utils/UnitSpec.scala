/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.ct.utils

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import uk.gov.hmrc.ct.box.CtValidation

trait UnitSpec extends AnyWordSpec with Matchers with Mocks {

 val validationSuccess: Set[CtValidation] = Set()

 val fieldRequiredError: String => Set[CtValidation] =
  boxId => Set(CtValidation(Some(boxId), s"error.$boxId.required", None))

 val mustBeZeroOrPositiveError: String => Set[CtValidation] =
  boxId => Set(CtValidation(Some(boxId), s"error.$boxId.mustBeZeroOrPositive", None))

 val postcodeError: String => Set[CtValidation] = boxId => Set(CtValidation(Some(boxId), s"error.$boxId.invalidPostcode"))

 val regexError: String => Set[CtValidation] = boxId => Set(CtValidation(Some(boxId), s"error.$boxId.regexFailure"))
}
