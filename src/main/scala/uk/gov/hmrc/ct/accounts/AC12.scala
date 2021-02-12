/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.validation.TurnoverValidation


case class AC12(value: Option[Int]) extends CtBoxIdentifier(name = "Current Turnover/Sales")
  with CtOptionalInteger
  with Input
  with ValidatableBox[AccountsBoxRetriever with FilingAttributesBoxValueRetriever]
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
    
    if(!isOpwEnabled && boxRetriever.abridgedFiling().value) {
      Set.empty
    } else {
        val errors = collectErrors(
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

      if(errors.isEmpty) {
        validateMoney(value)
      } else {
        errors
      }
    }
  }
}

object AC12 {
  def apply(value: Int): AC12 = AC12(Some(value))
}
