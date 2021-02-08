/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.utils

import org.scalatest.Assertion
import uk.gov.hmrc.ct.accounts.AC12
import uk.gov.hmrc.ct.accounts.frs10x.boxes.AC13
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.utils.CatoLimits.{turnoverHMRCMaximumValue, turnoverHMRCMaximumWithCommas}
import uk.gov.hmrc.ct.utils.UnitSpec

trait CovidProfitAndLossSpecHelper extends UnitSpec {

  private val defaultErrorArgs: Option[List[String]] = Some(List("0", turnoverHMRCMaximumWithCommas))

   val emptyTurnover: AC12 = AC12(Some(0))

   val emptyTurnoverPreviousPeriod: AC13 = AC13(Some(0))

   val justOverLimit: Int = turnoverHMRCMaximumValue + 1

   val justUnderLimit: Int = turnoverHMRCMaximumValue - 1

   val zeroOrPositiveErrorMsg: String = "mustBeZeroOrPositive"

   def turnoverBiggerThanMax(boxId: String) =  s"hmrc.turnover.$boxId.above.max"


  def boxValidationIsSuccessful(validation: Set[CtValidation]): Assertion = {
    validation shouldBe validationSuccess
  }

  def doesErrorMessageContain(validation: Set[CtValidation], errorMsgKey: String, shouldItContain: Boolean = true): Assertion =
    validation.head.errorMessageKey.contains(errorMsgKey) shouldBe shouldItContain

  def turnoverTooLargeErrorArguments(actualArgs: Set[CtValidation],
                                     expectedArgs: Option[Seq[String]] = defaultErrorArgs): Assertion =  {
    actualArgs.head.args shouldBe expectedArgs
  }



}
