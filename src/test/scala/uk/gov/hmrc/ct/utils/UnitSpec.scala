/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.utils

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.accounts.AC205
import uk.gov.hmrc.ct.box.CtValidation

trait UnitSpec extends WordSpec with MockitoSugar with Matchers {

 val mandatoryNotesStartDate: LocalDate = LocalDate.parse("2017-01-01")

 val previousPeriodOfAccounts:  AC205 = AC205(Some(LocalDate.now()))

 val validationSuccess: Set[CtValidation] = Set()

 val fieldRequiredError: String => Set[CtValidation] =
  boxId => Set(CtValidation(Some(boxId), s"error.$boxId.required", None))

 val postcodeError: String => Set[CtValidation] = boxId => Set(CtValidation(Some(boxId), s"error.$boxId.invalidPostcode"))

 val regexError: String => Set[CtValidation] = boxId => Set(CtValidation(Some(boxId), s"error.$boxId.regexFailure"))
}
