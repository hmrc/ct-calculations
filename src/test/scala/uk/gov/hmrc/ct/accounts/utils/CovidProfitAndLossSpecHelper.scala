/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.utils

import org.scalatest.Assertion
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.utils.CatoInputBounds._
import uk.gov.hmrc.ct.utils.UnitSpec

trait CovidProfitAndLossSpecHelper extends UnitSpec {

  private val defaultErrorArgs: Option[List[String]] = Some(List(minimumValueAsString, turnoverHMRCMaxWithCommas))
   val ac12Id: String = "AC12"
   val ac16Id: String = "AC16"

   def justOverLimit(max: Int): Int = max + 1
   def justUnderLimit(minimum: Int): Int = minimum - 1

   val justUnderLimit: Int = turnoverHMRCMaxValue632k - 1

   val zeroOrPositiveErrorMsg: String = "mustBeZeroOrPositive"

   def turnoverBiggerThanMax(boxId: String, isCohoJourney: Boolean): String =
     if(isCohoJourney) s"coho.turnover.$boxId.above.max"
      else s"hmrc.turnover.$boxId.above.max"

  def boxValidationIsSuccessful(validation: Set[CtValidation]): Assertion = validation shouldBe validationSuccess

  def doesErrorMessageContain(validatedBox: Set[CtValidation], errorMsgKey: String, validationErrorsMatchMessageKey: Boolean = true): Assertion =
    validatedBox.head.errorMessageKey.contains(errorMsgKey) shouldBe validationErrorsMatchMessageKey

  def turnoverTooLargeErrorArguments(validationResultArguments: Set[CtValidation],
                                     expectedArgs: Option[Seq[String]] = defaultErrorArgs): Assertion =  {
    validationResultArguments.head.args shouldBe expectedArgs
  }



}
