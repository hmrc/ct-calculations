/*
 * Copyright 2020 HM Revenue & Customs
 *
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
