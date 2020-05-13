/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.validations

import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalString, CtValidation, ValidatableBox}
import uk.gov.hmrc.ct.box.ValidatableBox._

trait ValidateDeclarationNameOrStatus[T <: BoxRetriever] extends ValidatableBox[T] {

  def validateDeclarationNameOrStatus(boxId: String, box: CtOptionalString with CtBoxIdentifier): Set[CtValidation] = {
    validateStringAsMandatory(boxId, box) ++ validateOptionalStringByLength(boxId, box, 2, 56) ++ validateOptionalStringByRegex(boxId, box, ValidNonForeignLessRestrictiveCharacters)
  }
}
