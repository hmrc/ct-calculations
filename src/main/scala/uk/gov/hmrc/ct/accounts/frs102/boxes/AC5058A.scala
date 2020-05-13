

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever._

case class AC5058A(value: Option[String]) extends CtBoxIdentifier(name = "Balance sheet - Creditors within 1 year note.") with CtOptionalString with Input with SelfValidatableBox[Frs102AccountsBoxRetriever, Option[String]] {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    val isMandatory = anyHaveValue(ac58(), ac59())

    collectErrors (
      failIf(!isMandatory)(
        validateCannotExist(boxRetriever)
      ),
      failIf(isMandatory)(
        validateNoteIsMandatory(boxRetriever)
      ),
      validateStringMaxLength(value.getOrElse(""), StandardCohoTextFieldLimit),
      validateCoHoStringReturnIllegalChars()
    )
  }

  private def validateCannotExist(boxRetriever: Frs102AccountsBoxRetriever)(): Set[CtValidation] = {
    boxRetriever match {
      case x: AbridgedAccountsBoxRetriever => failIf(hasValue)(Set(CtValidation(None, "error.balanceSheet.creditorsWithinOneYear.cannotExist")))
      case x: FullAccountsBoxRetriever => failIf(fullNoteHasValue(x))(Set(CtValidation(None, "error.balanceSheet.creditorsWithinOneYear.cannotExist")))
      case _ => Set.empty
    }
  }

  private def validateNoteIsMandatory(boxRetriever: Frs102AccountsBoxRetriever)(): Set[CtValidation] = {
    boxRetriever match {
      case x: FullAccountsBoxRetriever =>
        failIf(!fullNoteHasValue(x))(Set(CtValidation(None, "error.creditors.within.one.year.note.one.box.required")))
      case _ => Set.empty
    }
  }

  private def fullNoteHasValue(boxRetriever: FullAccountsBoxRetriever): Boolean = {
    import boxRetriever._

    anyHaveValue(
      ac142(), ac143(),
      ac144(), ac145(),
      ac146(), ac147(),
      ac148(), ac149(),
      ac150(), ac151(),
      ac152(), ac153(),
      this
    )
  }
}
