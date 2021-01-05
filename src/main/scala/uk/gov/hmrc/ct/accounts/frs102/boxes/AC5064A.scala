/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever._

case class AC5064A(value: Option[String]) extends CtBoxIdentifier(name = "Balance sheet - Creditors after 1 year note.")
                                          with CtOptionalString
                                          with Input
                                          with ValidatableBox[Frs102AccountsBoxRetriever] {

  private def fullNoteHasValue(boxRetriever: FullAccountsBoxRetriever): Boolean = {
    import boxRetriever._

    anyHaveValue(
      ac156(),
      ac157(),
      ac158(),
      ac159(),
      ac160(),
      ac161(),
      ac162(),
      ac163(),
      ac5064A()
    )
  }

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._
    val isMandatory = anyHaveValue(ac64(), ac65())

    collectErrors (
      failIf(!isMandatory)(validateCannotExist(boxRetriever)),
      failIf(isMandatory)(validateNoteIsMandatory(boxRetriever)),
      validateStringMaxLength("AC5064A", value.getOrElse(""), StandardCohoTextFieldLimit),
      validateCoHoStringReturnIllegalChars("AC5064A", this)
    )
  }

  private def validateCannotExist(boxRetriever: Frs102AccountsBoxRetriever)(): Set[CtValidation] = {
    boxRetriever match {
      case x: AbridgedAccountsBoxRetriever =>
        if (hasValue)
          Set(CtValidation(None, "error.balanceSheet.creditorsAfterOneYear.cannotExist"))
        else
          Set.empty

      case x: FullAccountsBoxRetriever =>
        if (fullNoteHasValue(x))
          Set(CtValidation(None, "error.balanceSheet.creditorsAfterOneYear.cannotExist"))
        else
          Set.empty
    }
  }

  private def validateNoteIsMandatory(boxRetriever: Frs102AccountsBoxRetriever)(): Set[CtValidation] = {
    boxRetriever match {
      case x: FullAccountsBoxRetriever =>
        if (!fullNoteHasValue(x))
          Set(CtValidation(None, "error.balanceSheet.creditorsAfterOneYear.mustNotBeEmpty"))
        else
          Set.empty

      case _ => Set.empty
    }
  }

}
