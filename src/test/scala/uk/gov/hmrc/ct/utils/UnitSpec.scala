/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.utils

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation

trait UnitSpec extends WordSpec with Matchers with Mocks {

 val validationSuccess: Set[CtValidation] = Set()

 val fieldRequiredError: String => Set[CtValidation] =
  boxId => Set(CtValidation(Some(boxId), s"error.$boxId.required", None))

 val mustBeZeroOrPositiveError: String => Set[CtValidation] =
  boxId => Set(CtValidation(Some(boxId), s"error.$boxId.mustBeZeroOrPositive", None))

 val postcodeError: String => Set[CtValidation] = boxId => Set(CtValidation(Some(boxId), s"error.$boxId.invalidPostcode"))

 val regexError: String => Set[CtValidation] = boxId => Set(CtValidation(Some(boxId), s"error.$boxId.regexFailure"))
}
