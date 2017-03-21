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
      builder(Some(-55)).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.mustBeZeroOrPositive"))
    }

    "pass must be zero or positive validation when zero" in {
      builder(Some(0)).validate(boxRetriever) shouldBe Set.empty
    }

    "pass must be zero or positive validation when positive" in {
      builder(Some(55)).validate(boxRetriever) shouldBe Set.empty
    }
  }

  def testBecauseOfDependendBoxThenCannotExist(boxId: String, builder: Option[Int] => ValidatableBox[T])(boxRetriever: => T) = {

    setUpMocks()

    "fail cannot exist validation when box has value" in {
      builder(Some(33)).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.cannot.exist"))
    }

    "pass cannot exist validation when box has no value" in {
      builder(None).validate(boxRetriever) shouldBe empty
    }
  }

}
