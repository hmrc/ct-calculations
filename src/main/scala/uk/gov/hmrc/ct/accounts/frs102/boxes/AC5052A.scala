/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever._


case class AC5052A(value: Option[Int]) extends CtBoxIdentifier(name = "Debtors due after more than one year") with CtOptionalInteger
                                                                                                              with Input
                                                                                                              with ValidatableBox[Frs102AccountsBoxRetriever]
                                                                                                              with SelfValidatableBox[Frs102AccountsBoxRetriever, Option[Int]]

with Validators {

  private def noteHasValue(boxRetriever: Frs102AccountsBoxRetriever): Boolean = {
    boxRetriever match {
      case x: AbridgedAccountsBoxRetriever => anyHaveValue(x.ac5052A(), x.ac5052B(), x.ac5052C())
      case x: FullAccountsBoxRetriever => anyHaveValue(x.ac134(), x.ac135(), x.ac138(), x.ac139(), x.ac136(), x.ac137(), x.ac140(), x.ac141(), x.ac5052A(), x.ac5052B(), x.ac5052C())
    }
  }

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    val isMandatory = anyHaveValue(ac52(), ac53())

    collectErrors (
      failIf(!isMandatory)(
        validateCannotExist(boxRetriever)
      ),
      failIf(isMandatory)(
        validateNotEmpty(boxRetriever)
      ),
      validateMoney(value, min = 0),
      validateOptionalIntegerLessOrEqualBox(boxRetriever.ac52())
    )
  }

  private def validateCannotExist(boxRetriever: Frs102AccountsBoxRetriever)(): Set[CtValidation] = {
    if (noteHasValue(boxRetriever))
      Set(CtValidation(None, "error.balanceSheet.debtors.cannotExist"))
    else
      Set.empty
  }

  private def validateNotEmpty(boxRetriever: Frs102AccountsBoxRetriever)(): Set[CtValidation] = {
    if (!noteHasValue(boxRetriever))
      Set(CtValidation(None, "error.balanceSheet.debtors.mustNotBeEmpty"))
    else
      Set.empty
  }

}
