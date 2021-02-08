/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.{AC3, AC4, AccountsPreviousPeriodValidation}
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.utils.CatoLimits
import uk.gov.hmrc.ct.validation.TurnoverValidation
import CatoLimits._

case class AC13(value: Option[Int]) extends CtBoxIdentifier(name = "Turnover (previous PoA)")
  with CtOptionalInteger
  with Input
  with ValidatableBox[AccountsBoxRetriever with FilingAttributesBoxValueRetriever]
  with AccountsPreviousPeriodValidation
  with TurnoverValidation {

  val accountsStart: AccountsBoxRetriever => AC3 = {
    boxRetriever: AccountsBoxRetriever =>
      boxRetriever.ac3()
  }

  val accountEnd: AccountsBoxRetriever => AC4 = {
    boxRetriever: AccountsBoxRetriever =>
      boxRetriever.ac4()
  }

  override def validate(boxRetriever: AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    val isOpwEnabled = boxRetriever.cato24().value.getOrElse(false)

    if (!isOpwEnabled && boxRetriever.abridgedFiling().value) {
      Set.empty
    } else {
      val errors = collectErrors(
        validateInputAllowed("AC13", boxRetriever.ac205()),
        failIf(isFrs10xHmrcAbridgedReturnWithLongPoA(accountsStart, accountEnd)(boxRetriever)) {
          validateAsMandatory(this)
        },
        failIf(boxRetriever.hmrcFiling().value)(
          collectErrors(
            validateHmrcTurnover(boxRetriever, accountsStart, accountEnd)
          )
        ),
        failIf(!boxRetriever.hmrcFiling().value && boxRetriever.companiesHouseFiling().value)(
          collectErrors(
            validateCoHoTurnover(boxRetriever, accountsStart, accountEnd)
          )
        ),
        validateZeroOrPositiveInteger(this)
      )

      if (errors.isEmpty) {
        validateMoney(value, minimumValue, turnoverHMRCMaximumValue)
      } else {
        errors
      }
    }
  }
}