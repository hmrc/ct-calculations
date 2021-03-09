/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.{AC3, AC4}
import uk.gov.hmrc.ct.accounts.frs102.calculations.GrossProfitAndLossCalculator
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.utils.CatoInputBounds
import uk.gov.hmrc.ct.validation.TurnoverValidation

case class AC16(value: Option[Int]) extends CtBoxIdentifier(name = "Gross profit or loss (current PoA)")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever]
  with Validators with TurnoverValidation {

  val accountsStart: Frs10xAccountsBoxRetriever => AC3 = {
    boxRetriever: AccountsBoxRetriever =>
      boxRetriever.ac3()
  }

  val accountEnd: Frs10xAccountsBoxRetriever => AC4 = {
    boxRetriever: AccountsBoxRetriever =>
      boxRetriever.ac4()
  }

  private val isAbridgedFiling: FilingAttributesBoxValueRetriever => Boolean = boxRetriever =>
    boxRetriever.abridgedFiling().value

  private val isHmrcFiling: FilingAttributesBoxValueRetriever => Boolean = boxRetriever =>
    boxRetriever.hmrcFiling().value

  private val isJoint: FilingAttributesBoxValueRetriever => Boolean = boxRetriever =>
    boxRetriever.isJointFiling()

  private val isFullFiling: FilingAttributesBoxValueRetriever => Boolean = boxRetriever =>
    boxRetriever.statutoryAccountsFiling().value

  override def validate(boxRetriever: Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever ): Set[CtValidation] = {

//    val asd: Boolean = if (isHmrcFiling(boxRetriever)) {
//      isAbridgedFiling(boxRetriever) || isFullFiling(boxRetriever)
//    } else if (isJoint(boxRetriever)) {
//      isAbridgedFiling(boxRetriever) || isFullFiling(boxRetriever)
//    } else false

    collectErrors(
      validateAsMandatory(this),
      failIf(isHmrcFiling(boxRetriever))(
      collectErrors(
        validateHmrcTurnover(boxRetriever, accountsStart, accountEnd, minimumAmount = Some(CatoInputBounds.oldMinValue99999999))
      )
    ),
    failIf(!isHmrcFiling(boxRetriever) && boxRetriever.companiesHouseFiling().value)(
      collectErrors(
        validateCoHoTurnover(boxRetriever, accountsStart, accountEnd)
      )
    )
    )
  }
}
object AC16 extends Calculated[AC16, Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever] with GrossProfitAndLossCalculator {
  override def calculate(boxRetriever: Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever ): AC16 = {
    if(!boxRetriever.cato24().isTrue && boxRetriever.abridgedFiling().value) {
      AC16(None)
    } else {
      calculateAC16(boxRetriever.ac12, boxRetriever.ac24, boxRetriever.ac401, boxRetriever.ac403, boxRetriever.ac14())
    }
  }
}