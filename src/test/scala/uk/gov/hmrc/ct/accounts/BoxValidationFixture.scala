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

import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

trait BoxValidationFixture[T <: ComputationsBoxRetriever] extends WordSpec with Matchers with MockitoSugar {

  def boxRetriever: T

  //This can be overridden if mock box retriever calls need to be made
  def setUpMocks(): Unit = Unit

  def testBoxIsZeroOrPositive(boxId: String, builder: (Int) => ValidatableBox[T]) = {

    setUpMocks()

    "fail validation when negative" in {
      builder(-55).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.mustBeZeroOrPositive"))
    }

    "pass validation when zero" in {
      builder(0).validate(boxRetriever) shouldBe Set.empty
    }

    "pass validation when positive" in {
      builder(55).validate(boxRetriever) shouldBe Set.empty
    }
  }

}
