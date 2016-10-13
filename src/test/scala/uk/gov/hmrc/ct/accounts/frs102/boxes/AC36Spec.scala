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

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.AC12
import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box.CtValidation

class AC36Spec extends WordSpec with Matchers with MockitoSugar  {

  "AC36" should {
    "for Abridged Accounts" when {
      val boxRetriever = mock[AbridgedAccountsBoxRetriever]
      "pass validation if all fields have a valid value" in {
        when(boxRetriever.ac16()).thenReturn(AC16(Some(16)))
        when(boxRetriever.ac18()).thenReturn(AC18(Some(18)))
        when(boxRetriever.ac20()).thenReturn(AC20(Some(20)))
        when(boxRetriever.ac28()).thenReturn(AC28(Some(28)))
        when(boxRetriever.ac30()).thenReturn(AC30(Some(30)))
        when(boxRetriever.ac34()).thenReturn(AC34(Some(34)))
        AC36(Some(10)).validate(boxRetriever) shouldBe empty
      }
      "pass validation if AC16 field has a valid value" in {
        when(boxRetriever.ac16()).thenReturn(AC16(Some(16)))
        when(boxRetriever.ac18()).thenReturn(AC18(None))
        when(boxRetriever.ac20()).thenReturn(AC20(None))
        when(boxRetriever.ac28()).thenReturn(AC28(None))
        when(boxRetriever.ac30()).thenReturn(AC30(None))
        when(boxRetriever.ac34()).thenReturn(AC34(None))
        AC36(Some(16)).validate(boxRetriever) shouldBe empty
      }
      "pass validation if AC18 field has a valid value" in {
        when(boxRetriever.ac16()).thenReturn(AC16(None))
        when(boxRetriever.ac18()).thenReturn(AC18(Some(18)))
        when(boxRetriever.ac20()).thenReturn(AC20(None))
        when(boxRetriever.ac28()).thenReturn(AC28(None))
        when(boxRetriever.ac30()).thenReturn(AC30(None))
        when(boxRetriever.ac34()).thenReturn(AC34(None))
        AC36(Some(16)).validate(boxRetriever) shouldBe empty
      }
      "fail validation if all current inputs are empty" in {
        when(boxRetriever.ac16()).thenReturn(AC16(None))
        when(boxRetriever.ac18()).thenReturn(AC18(None))
        when(boxRetriever.ac20()).thenReturn(AC20(None))
        when(boxRetriever.ac28()).thenReturn(AC28(None))
        when(boxRetriever.ac30()).thenReturn(AC30(None))
        when(boxRetriever.ac34()).thenReturn(AC34(None))
        AC36(None).validate(boxRetriever) shouldBe Set(CtValidation(boxId = None, "error.profit.loss.one.box.required"))
      }
    }
    "for Full Accounts" when {
      val boxRetriever = mock[FullAccountsBoxRetriever]
      "pass validation if all fields have a valid value" in {
        when(boxRetriever.ac12()).thenReturn(AC12(Some(12)))
        when(boxRetriever.ac14()).thenReturn(AC14(Some(14)))
        when(boxRetriever.ac18()).thenReturn(AC18(Some(18)))
        when(boxRetriever.ac20()).thenReturn(AC20(Some(20)))
        when(boxRetriever.ac28()).thenReturn(AC28(Some(28)))
        when(boxRetriever.ac30()).thenReturn(AC30(Some(30)))
        when(boxRetriever.ac34()).thenReturn(AC34(Some(34)))
        AC36(Some(36)).validate(boxRetriever) shouldBe empty
      }
      "pass validation if 1 field has a valid value" in {
        when(boxRetriever.ac12()).thenReturn(AC12(Some(12)))
        when(boxRetriever.ac14()).thenReturn(AC14(None))
        when(boxRetriever.ac18()).thenReturn(AC18(None))
        when(boxRetriever.ac20()).thenReturn(AC20(None))
        when(boxRetriever.ac28()).thenReturn(AC28(None))
        when(boxRetriever.ac30()).thenReturn(AC30(None))
        when(boxRetriever.ac34()).thenReturn(AC34(None))
        AC36(Some(12)).validate(boxRetriever) shouldBe empty
      }
      "pass validation if 1 (shared) field has a valid value" in {
        when(boxRetriever.ac12()).thenReturn(AC12(None))
        when(boxRetriever.ac14()).thenReturn(AC14(None))
        when(boxRetriever.ac18()).thenReturn(AC18(Some(18)))
        when(boxRetriever.ac20()).thenReturn(AC20(None))
        when(boxRetriever.ac28()).thenReturn(AC28(None))
        when(boxRetriever.ac30()).thenReturn(AC30(None))
        when(boxRetriever.ac34()).thenReturn(AC34(None))
        AC36(Some(18)).validate(boxRetriever) shouldBe empty
      }
      "fail validation if all current inputs are empty" in {
        when(boxRetriever.ac12()).thenReturn(AC12(None))
        when(boxRetriever.ac14()).thenReturn(AC14(None))
        when(boxRetriever.ac18()).thenReturn(AC18(None))
        when(boxRetriever.ac20()).thenReturn(AC20(None))
        when(boxRetriever.ac28()).thenReturn(AC28(None))
        when(boxRetriever.ac30()).thenReturn(AC30(None))
        when(boxRetriever.ac34()).thenReturn(AC34(None))
        AC36(None).validate(boxRetriever) shouldBe Set(CtValidation(boxId = None, "error.profit.loss.one.box.required"))
      }
    }
  }
}
