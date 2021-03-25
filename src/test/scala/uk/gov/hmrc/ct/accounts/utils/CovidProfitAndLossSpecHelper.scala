/*
 * Copyright 2021 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
