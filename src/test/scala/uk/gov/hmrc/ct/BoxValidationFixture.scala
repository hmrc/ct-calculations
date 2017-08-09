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

package uk.gov.hmrc.ct

import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

trait BoxValidationFixture[T <: ComputationsBoxRetriever] extends WordSpec with Matchers with MockitoSugar with BeforeAndAfter {

  def boxRetriever: T

  //This can be overridden if mock box retriever calls need to be made
  def setUpMocks(): Unit = Unit

  def testBoxIsZeroOrPositive(boxId: String, builder: Option[Int] => ValidatableBox[T]) = {

    setUpMocks()

    "fail must be zero or positive validation when negative" in {
      builder(Some(-55)).validate(boxRetriever).contains(CtValidation(Some(boxId), s"error.$boxId.mustBeZeroOrPositive")) shouldBe true
    }

    "pass must be zero or positive validation when zero" in {
      builder(Some(0)).validate(boxRetriever).contains(CtValidation(Some(boxId), s"error.$boxId.mustBeZeroOrPositive")) shouldBe false
    }

    "pass must be zero or positive validation when positive" in {
      builder(Some(55)).validate(boxRetriever).contains(CtValidation(Some(boxId), s"error.$boxId.mustBeZeroOrPositive")) shouldBe false
    }
  }

  def testMandatoryWhen(boxId: String, builder: Option[Int] => ValidatableBox[T])(boxRetriever: => T) = {

    setUpMocks()

    "fail when no value" in {
      builder(None).validate(boxRetriever).contains(CtValidation(Some(boxId), s"error.$boxId.required")) shouldBe true
    }

    "pass when has value" in {
      builder(Some(1)).validate(boxRetriever).contains(CtValidation(Some(boxId), s"error.$boxId.required")) shouldBe false
    }
  }

  def testCannotExistWhen(boxId: String, builder: Option[Int] => ValidatableBox[T], testDetails: String = "")(boxRetriever: => T) = {

    setUpMocks()

    s"fail cannot exist validation when box has value $testDetails" in {
      builder(Some(33)).validate(boxRetriever).contains(CtValidation(Some(boxId), s"error.$boxId.cannot.exist")) shouldBe true
    }

    s"pass cannot exist validation when box has no value $testDetails" in {
      builder(None).validate(boxRetriever).contains(CtValidation(Some(boxId), s"error.$boxId.cannot.exist")) shouldBe false
    }
  }

}
