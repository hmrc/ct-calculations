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
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs10x.abridged.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC5032Spec extends WordSpec with MockitoSugar with Matchers {

  val boxRetriever = mock[AbridgedAccountsBoxRetriever]

  "AC5032" should {
    "pass validation when empty" in {
      when(boxRetriever.ac32()).thenReturn(AC32(Some(1)))
      AC5032(None).validate(boxRetriever) shouldBe Set.empty
    }
    "pass validation when empty string" in {
      when(boxRetriever.ac32()).thenReturn(AC32(Some(1)))
      AC5032(Some("")).validate(boxRetriever) shouldBe Set.empty
    }
    "pass validation when string" in {
      when(boxRetriever.ac32()).thenReturn(AC32(Some(1)))
      AC5032(Some("testing this like crazy")).validate(boxRetriever) shouldBe Set.empty
    }
    "pass validation when string is 20,000 characters long" in {
      when(boxRetriever.ac32()).thenReturn(AC32(Some(1)))
      val string = (1 to 20000).map(x => "a").mkString("")
      AC5032(Some(string)).validate(boxRetriever) shouldBe Set.empty
    }
    "fail validation when string is longer than 20,000 characters long" in {
      when(boxRetriever.ac32()).thenReturn(AC32(Some(1)))
      val string = (1 to 20001).map(x => "a").mkString("")
      AC5032(Some(string)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC5032"), "error.AC5032.max.length", Some(Seq("20,000"))))
    }
    "fail validation when populated and AC32 is empty" in {
      when(boxRetriever.ac32()).thenReturn(AC32(None))
      AC5032(Some("testing")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC5032"), "error.AC5032.cannot.exist"))
    }
  }
}
