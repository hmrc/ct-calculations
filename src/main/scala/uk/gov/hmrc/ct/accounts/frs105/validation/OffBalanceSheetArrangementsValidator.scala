

package uk.gov.hmrc.ct.accounts.frs105.validation

import uk.gov.hmrc.ct.accounts.frs105.boxes.{AC7999, AC7999a}
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtValidation, ValidatableBox}

trait OffBalanceSheetArrangementsValidator {

  self: ValidatableBox[Frs105AccountsBoxRetriever] with CtBoxIdentifier =>

  def validateAgainstAC7999a(boxRetriever: Frs105AccountsBoxRetriever, boxId: String, value: Option[String]): Set[CtValidation] = {
    (boxRetriever.ac7999a(), value) match {
      case (AC7999a(Some(true)), None) => validateStringAsMandatoryWithNoTrailingWhitespace(boxId, AC7999(value))
      case (AC7999a(Some(true)), Some("")) => validateStringAsMandatoryWithNoTrailingWhitespace(boxId, AC7999(value))
      case (_, _) => Set()
    }
  }
}
