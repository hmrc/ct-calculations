package uk.gov.hmrc.ct.box

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.domain.ValidationConstants.toErrorArgsFormat
import uk.gov.hmrc.ct.utils.DateImplicits._

trait ExtraValidation {
  def validateAsMandatory[A](boxId: String, value: Option[A])(): Set[CtValidation] = {
    value match {
      case None => Set(CtValidation(Some(boxId), s"error.$boxId.required"))
      case Some(x: String) if x.isEmpty => Set(CtValidation(Some(boxId), s"error.$boxId.required"))
      case _ => Set()
    }
  }

  def validateDateIsInclusive(boxId: String, minDate: LocalDate, dateToCompare: Option[LocalDate], maxDate: LocalDate): Set[CtValidation] = {
    dateToCompare match {
      case None => Set()
      case Some(dateToComp) if minDate < dateToComp && dateToComp < maxDate => Set()
      case _ => Set(CtValidation(Some(boxId), s"error.$boxId.not.betweenInclusive", Some(Seq(toErrorArgsFormat(minDate), toErrorArgsFormat(maxDate)))))
    }
  }
}
