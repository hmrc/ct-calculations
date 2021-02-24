/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.utils

import org.scalatest.Assertion
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.utils.CatoLimits._
import uk.gov.hmrc.ct.utils.UnitSpec

import scala.util.{Failure, Success, Try}

trait CovidProfitAndLossSpecHelper extends UnitSpec {

  private val defaultErrorArgs: Option[List[String]] = Some(List(minimumValueAsString, turnoverHMRCMaxWithCommas))
   val ac12Id: String = "AC12"
   val ac16Id: String = "AC16"

   def justOverLimit(max: Int): Int = max + 1
   def justUnderLimit(minimum: Int): Int = minimum - 1

   val justUnderLimit: Int = turnoverHMRCMaxValue - 1

   val zeroOrPositiveErrorMsg: String = "mustBeZeroOrPositive"

   def turnoverBiggerThanMax(boxId: String, isCohoJourney: Boolean) =
     if(isCohoJourney) s"coho.turnover.$boxId.above.max"
      else s"hmrc.turnover.$boxId.above.max"

  def boxValidationIsSuccessful(validation: Set[CtValidation]): Assertion = validation shouldBe validationSuccess

  def doesErrorMessageContain(validation: Set[CtValidation], errorMsgKey: String, shouldItContain: Boolean = true): Assertion =
    validation.head.errorMessageKey.contains(errorMsgKey) shouldBe shouldItContain

  def turnoverTooLargeErrorArguments(actualArgs: Set[CtValidation],
                                     expectedArgs: Option[Seq[String]] = defaultErrorArgs): Assertion =  {
    actualArgs.head.args shouldBe expectedArgs
  }



}
