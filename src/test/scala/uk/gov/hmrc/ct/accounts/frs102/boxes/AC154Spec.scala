/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC154Spec extends WordSpec with Matchers with MockitoSugar {

  "AC154" should {

    "have no errors if AC58 has the same value" in {
      val boxRetriever = mock[FullAccountsBoxRetriever]

      when(boxRetriever.ac58()).thenReturn(AC58(Some(50)))
      when(boxRetriever.ac59()).thenReturn(AC59(Some(50)))
      AC154(Some(50)).validate(boxRetriever) shouldBe empty
    }


    "have no errors if AC58 and AC154 are both None" in {
      val boxRetriever = mock[FullAccountsBoxRetriever]

      when(boxRetriever.ac58()).thenReturn(AC58(None))
      when(boxRetriever.ac59()).thenReturn(AC59(Some(50)))
      AC154(None).validate(boxRetriever) shouldBe empty
    }

    "have no errors if AC58 and AC154 are both 0" in {
      val boxRetriever = mock[FullAccountsBoxRetriever]

      when(boxRetriever.ac58()).thenReturn(AC58(Some(0)))
      when(boxRetriever.ac59()).thenReturn(AC59(Some(50)))
      AC154(Some(0)).validate(boxRetriever) shouldBe empty
    }

    "return error if AC58 is 0 and AC154 is None" in {
      val boxRetriever = mock[FullAccountsBoxRetriever]

      when(boxRetriever.ac58()).thenReturn(AC58(Some(0)))
      when(boxRetriever.ac59()).thenReturn(AC59(Some(50)))
      AC154(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.creditors.within.one.year.note.current.total.not.equal.balance.sheet"))
    }

    "return error if AC58 is 0 and AC154 is 50" in {
      val boxRetriever = mock[FullAccountsBoxRetriever]

      when(boxRetriever.ac58()).thenReturn(AC58(Some(0)))
      when(boxRetriever.ac59()).thenReturn(AC59(Some(50)))
      AC154(Some(50)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.creditors.within.one.year.note.current.total.not.equal.balance.sheet"))
    }

  }

}
