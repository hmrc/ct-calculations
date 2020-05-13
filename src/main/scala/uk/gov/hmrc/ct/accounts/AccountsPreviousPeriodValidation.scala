

package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.box.{CtValidation, CtValue}

trait AccountsPreviousPeriodValidation {

  self: CtValue[Option[Int]] =>

  def validateInputAllowed(boxId: String, ac205: AC205)(): Set[CtValidation] = {
    (value, ac205.value) match {
      case (Some(x), None) => Set(CtValidation(boxId = Some(boxId), s"error.$boxId.cannot.exist"))
      case _ => Set.empty
    }
  }
}
