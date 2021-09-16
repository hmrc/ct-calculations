/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.validation

import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtOptionalInteger, CtValidation, ValidatableBox}

trait AssetsEqualToSharesValidator extends ValidatableBox[Frs105AccountsBoxRetriever] {
  self: CtOptionalInteger =>

  def validateAssetsEqualToShares(boxId: String, otherBox: CtOptionalInteger)(): Set[CtValidation] = {
    failIf(value != otherBox.value) {
      Set(CtValidation(None, s"error.$boxId.assetsNotEqualToShares"))
    }
  }

}
