

package uk.gov.hmrc.ct.accounts.utils

import org.scalatest.mock.MockitoSugar
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
