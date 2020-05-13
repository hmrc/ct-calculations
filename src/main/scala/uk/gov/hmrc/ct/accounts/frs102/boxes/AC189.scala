

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDormancyBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever._

case class AC189(value: Option[Int]) extends CtBoxIdentifier(name = "Surplus or deficit after revaluation") with CtOptionalInteger
                                                                                                              with CtTypeConverters
                                                                                                              with Input
                                                                                                              with ValidatableBox[Frs102AccountsBoxRetriever with Frs10xDormancyBoxRetriever]
                                                                                                              with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever with Frs10xDormancyBoxRetriever): Set[CtValidation] = {
    import boxRetriever._
    val hasReserve = anyHaveValue(ac76(), ac77())
    val dormant = boxRetriever.acq8999().orFalse

    collectErrors (
      failIf(hasReserve && (!dormant || boxRetriever.ac187()))(validateIntegerAsMandatory("AC189", this)),
      failIf(!hasReserve)(validateNoteCannotExist(boxRetriever)),
      validateMoney(value)
    )
  }

  def validateNoteCannotExist(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    val isNoteNonEmpty = ac189().hasValue || ac5076C().value.getOrElse("").trim().nonEmpty

    if (isNoteNonEmpty)
      Set(CtValidation(None, "error.balanceSheet.revaluationReserveNote.cannot.exist"))
    else
      Set.empty
  }

}
