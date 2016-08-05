/*
 * Copyright 2016 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs10x.abridged

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs10x.abridged.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC5052BSpec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfter{

  val boxRetriever = mock[AbridgedAccountsBoxRetriever]

  before {
    when(boxRetriever.ac52).thenReturn(AC52(Some(33)))
  }

  "AC5052B" should {
    "pass validation when empty" in {

      AC5052B(None).validate(boxRetriever) shouldBe Set.empty
    }
    "pass validation when empty string" in {

      AC5052B(Some("")).validate(boxRetriever) shouldBe Set.empty
    }
    "pass validation when string" in {

      AC5052B(Some("testing this like crazy")).validate(boxRetriever) shouldBe Set.empty
    }
    "pass validation when string is 20,000 characters long" in {

      val string = "a" * 20000
      AC5052B(Some(string)).validate(boxRetriever) shouldBe Set.empty
    }
    "fail validation when string is longer than 20,000 characters long" in {

      val string = "a" * 20001
      AC5052B(Some(string)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC5052B"), "error.AC5052B.max.length", Some(Seq("20,000"))))
    }

    "fail validation if invalid characters" in {

      AC5052B(Some("??")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC5052B"), "error.AC5052B.regexFailure", None))
    }

    "fail validation when populated and AC52 is empty" in {

      when(boxRetriever.ac52()).thenReturn(AC52(None))
      AC5052B(Some("test text")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC5052B"), "error.AC5052B.cannot.exist"))
    }
  }
}
