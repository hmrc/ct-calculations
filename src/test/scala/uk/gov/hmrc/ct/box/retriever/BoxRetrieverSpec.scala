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

package uk.gov.hmrc.ct.box.retriever

import org.scalatest.Matchers._
import org.scalatest.WordSpec
import uk.gov.hmrc.ct.box._

class BoxRetrieverSpec extends WordSpec {

  val fakeBox1Validation = CtValidation(Some("FakeBox1"), "fake1")
  val fakeBox2Validation = CtValidation(Some("FakeBox2"), "fake2")

  "Box Retriever" should {
    "return validated box errors" in new BoxRetriever {
      override def generateValues: Map[String, CtValue[_]] = ???

      val values = Map(
        "FakeBox1" -> FakeBox1(""),
        "FakeBox2" -> FakeBox2("")
      )

      val errors = validateValues(values)

      errors.size shouldBe 2
      errors should contain(fakeBox1Validation)
      errors should contain(fakeBox2Validation)
    }
  }

  case class FakeBox1(value: String) extends CtBoxIdentifier("poo") with Input with CtString with ValidatableBox[BoxRetriever] {
    override def validate(boxRetriever: BoxRetriever): Set[CtValidation] = {
      Set(fakeBox1Validation)
    }
  }

  case class FakeBox2(value: String) extends CtBoxIdentifier("poo") with Input with CtString with ValidatableBox[BoxRetriever] {
    override def validate(boxRetriever: BoxRetriever): Set[CtValidation] = {
      Set(fakeBox2Validation)
    }
  }

}
