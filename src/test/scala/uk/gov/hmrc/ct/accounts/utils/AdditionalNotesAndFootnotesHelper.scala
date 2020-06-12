/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.utils

import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation

trait AdditionalNotesAndFootnotesHelper extends WordSpec with Matchers with MockitoSugar {

  val input: String = "Some very off balance arrangements"

  val validationSuccess: Set[CtValidation] = Set.empty

  val boxId: String

   val minNumberOfEmployees = 0
   val maxNumberOfEmployees = 99999

  val fieldRequiredError: String => Set[CtValidation] =
    boxId => Set(CtValidation(Some(boxId), s"error.$boxId.required", None))
}
