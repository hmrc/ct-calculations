

package uk.gov.hmrc.ct.ct600e.validations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalString, CtValidation}

trait ValidateRegisteredCharityNumber {

  def validateRegisteredCharityNumber(box: CtOptionalString with CtBoxIdentifier): Set[CtValidation] = box.value match {
    case Some(v) if v.length < 6 || v.length > 8 || v.exists(!_.isDigit) => Set(CtValidation(Some(box.id), s"error.${box.id}.invalidRegNumber"))
    case _ => Set()
  }

}
