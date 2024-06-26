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

package uk.gov.hmrc.ct.accounts.validation

import uk.gov.hmrc.ct.box.{CtOptionalInteger, CtValidation, Validators}

trait AssetsEqualToSharesValidator extends Validators {
  self: CtOptionalInteger =>

  def validateAssetsEqualToShares(boxId: String, otherBox: CtOptionalInteger, isLimitedByGuarantee: Boolean): Set[CtValidation] = {
    collectErrors(
      failIf(value != otherBox.value && !isLimitedByGuarantee) {
        Set(CtValidation(None, s"error.$boxId.assetsNotEqualToShares"))
      },
      failIf(value != otherBox.value && isLimitedByGuarantee) {
        Set(CtValidation(None, s"error.$boxId.assetsNotEqualToMembersFunds"))
      }
    )
  }

}
