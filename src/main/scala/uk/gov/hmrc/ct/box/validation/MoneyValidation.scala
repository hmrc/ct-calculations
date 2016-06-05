package uk.gov.hmrc.ct.box.validation

import uk.gov.hmrc.ct.box.{CtOptionalInteger, CtValidation}
import uk.gov.hmrc.ct.domain.ValidationConstants._

trait OptionalMoneyValidation {

  self: CtOptionalInteger =>

  protected def validateMoneyRange(boxId: String, min: Int = MIN_MONEY_AMOUNT_ALLOWED, max: Int = MAX_MONEY_AMOUNT_ALLOWED): Set[CtValidation] = {
    value match {
      case Some(money) if money < min => Set(CtValidation(Some(boxId), s"error.$boxId.below.min"))
      case Some(money) if money > max => Set(CtValidation(Some(boxId), s"error.$boxId.exceeds.max"))
      case _ => Set.empty
    }
  }
}
