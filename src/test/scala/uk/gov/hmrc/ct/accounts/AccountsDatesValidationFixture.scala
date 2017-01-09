/*
 * Copyright 2017 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts

import org.joda.time.LocalDate
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}
import uk.gov.hmrc.ct.domain.ValidationConstants._

trait AccountsDatesValidationFixture[T <: AccountsBoxRetriever] extends WordSpec with Matchers with MockitoSugar {

  def boxRetriever: T

  //This can be overridden if mock box retriever calls need to be made
  def setUpMocks(): Unit = Unit

  def testDateIsMandatory(boxId: String, builder: (Option[LocalDate]) => ValidatableBox[T]): Unit = {
    setUpMocks()
    "fail validation when empty" in {
      builder(None).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.required"))
    }
  }

  def testDateBetweenIntervalValidation(boxId: String, startDate: LocalDate, endDate: LocalDate, builder: (Option[LocalDate]) => ValidatableBox[T]): Unit = {

    val INVALID_DATE_ERROR_MESSAGE: Set[CtValidation] = Set(CtValidation(Some(boxId), s"error.$boxId.not.betweenInclusive", Some(Seq(toErrorArgsFormat(startDate), toErrorArgsFormat(endDate)))))

    "fail validation when date before start date" in {
      builder(Some(startDate.minusDays(1))).validate(boxRetriever) shouldBe INVALID_DATE_ERROR_MESSAGE
    }
    "fail validation when date after end date" in {
      builder(Some(endDate.plusDays(1))).validate(boxRetriever) shouldBe INVALID_DATE_ERROR_MESSAGE
    }
    "pass validation when date is start date" in {
      builder(Some(startDate)).validate(boxRetriever) shouldBe Set()
    }
    "pass validation when date is end date" in {
      builder(Some(endDate)).validate(boxRetriever) shouldBe Set()
    }
    "pass validation when date is between start and end date" in {
      builder(Some(startDate.plusDays(1))).validate(boxRetriever) shouldBe Set()
    }
  }

}
