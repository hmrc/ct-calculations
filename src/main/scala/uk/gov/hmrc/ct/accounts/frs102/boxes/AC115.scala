/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.IntangibleAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever._


case class AC115(value: Option[Int]) extends CtBoxIdentifier(name = "Additions")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs102AccountsBoxRetriever]
  with Validators {

  private def getNoteValues(boxRetriever: Frs102AccountsBoxRetriever) = {
    boxRetriever match {
      case x: AbridgedAccountsBoxRetriever =>
        import x._
        Set(
          ac114().value,
          ac115().value,
          ac116().value,
          ac209().value,
          ac210().value,
          ac118().value,
          ac119().value,
          ac120().value,
          ac211().value
        )

      case x: FullAccountsBoxRetriever =>
        import x._
        Set(
          ac114().value,
          ac114A().value,
          ac114B().value,
          ac115().value,
          ac115A().value,
          ac115B().value,
          ac116().value,
          ac116A().value,
          ac116B().value,
          ac117().value,
          ac117A().value,
          ac117B().value,
          ac118().value,
          ac118A().value,
          ac118B().value,
          ac119().value,
          ac119A().value,
          ac119B().value,
          ac120().value,
          ac120A().value,
          ac120B().value,
          ac121().value,
          ac121A().value,
          ac121B().value,
          ac122().value,
          ac122A().value,
          ac122B().value,
          ac123().value,
          ac123A().value,
          ac123B().value,
          ac209().value,
          ac209A().value,
          ac209B().value,
          ac210().value,
          ac210A().value,
          ac210B().value,
          ac211().value,
          ac211A().value,
          ac211B().value
        )
    }
  }

  private def validateNoteEntered(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    val noteValues = getNoteValues(boxRetriever)
    val noteIsNotEmpty = noteValues.exists(_.nonEmpty) || ac5123().value.getOrElse("").trim().nonEmpty

    if (!noteIsNotEmpty)
      Set(CtValidation(None, "error.balanceSheet.intangibleAssets.atLeastOneEntered"))
    else
      Set.empty
  }

  private def validateNoteCannotExists(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    val noteValues = getNoteValues(boxRetriever)
    val noteIsNotEmpty = noteValues.exists(_.nonEmpty) || ac5123().value.getOrElse("").trim().nonEmpty

    if (noteIsNotEmpty)
      Set(CtValidation(None, "error.balanceSheet.intangibleAssetsNote.cannot.exist"))
    else
      Set.empty
  }

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._
    val isMandatory = anyHaveValue(ac42(), ac43())

    collectErrors(
      failIf(isMandatory)(validateNoteEntered(boxRetriever)),
      failIf(!isMandatory)(validateNoteCannotExists(boxRetriever)),
      validateMoney(value, min = 0)
    )
  }

}

object AC115 extends Calculated[AC115, FullAccountsBoxRetriever]
  with IntangibleAssetsCalculator {

  override def calculate(boxRetriever: FullAccountsBoxRetriever): AC115 = {
    import boxRetriever._
    calculateAC115(ac115A(), ac115B())
  }

}
