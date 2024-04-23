/*
 * Copyright 2024 HM Revenue & Customs
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
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import uk.gov.hmrc.ct.accounts.MockFrs102AccountsRetriever
import uk.gov.hmrc.ct.accounts.frs10x.boxes.ACQ8999
import uk.gov.hmrc.ct.box.CtValidation

class AC190Spec extends AnyWordSpec
  with MockitoSugar
  with Matchers
  with MockFrs102AccountsRetriever {

  "AC190" should {
    "fail validation when calculated value is not equal to AC76" in {
      when(boxRetriever.acq8999()).thenReturn(ACQ8999(Some(false)))
      when(boxRetriever.ac187()).thenReturn(AC187(Some(true)))
      when(boxRetriever.ac76()).thenReturn(AC76(Some(123)))
      AC190(Some(125)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.AC190.mustEqual.AC76"))
    }

    "pass validation when AC190 is None but the company is dormant" in {
      when(boxRetriever.acq8999()).thenReturn(ACQ8999(Some(true)))
      when(boxRetriever.ac187()).thenReturn(AC187(None))
      when(boxRetriever.ac76()).thenReturn(AC76(Some(123)))
      AC190(Some(125)).validate(boxRetriever) shouldBe empty
    }

    "fail validation when the company is dormant but a note is added and AC190 doesn't equal AC76" in {
      when(boxRetriever.acq8999()).thenReturn(ACQ8999(Some(true)))
      when(boxRetriever.ac187()).thenReturn(AC187(Some(true)))
      when(boxRetriever.ac76()).thenReturn(AC76(Some(123)))
      AC190(Some(125)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.AC190.mustEqual.AC76"))
    }

    "fail validation when calculated value is not equal to empty AC76 and AC77 is present" in {
      when(boxRetriever.acq8999()).thenReturn(ACQ8999(Some(false)))
      when(boxRetriever.ac187()).thenReturn(AC187(Some(true)))
      when(boxRetriever.ac76()).thenReturn(AC76(None))
      when(boxRetriever.ac77()).thenReturn(AC77(Some(1)))
      AC190(Some(125)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.AC190.mustEqual.AC76"))
    }

    "pass validation when calculated value is equal to 0 and AC76 is empty" in {
      when(boxRetriever.ac76()).thenReturn(AC76(None))
      when(boxRetriever.ac77()).thenReturn(AC77(Some(0)))
      AC190(Some(0)).validate(boxRetriever) shouldBe Set.empty
    }

    "pass validation when calculated value is equal to 0 and AC76 is 0" in {
      when(boxRetriever.ac76()).thenReturn(AC76(Some(0)))
      when(boxRetriever.ac77()).thenReturn(AC77(Some(0)))
      AC190(Some(0)).validate(boxRetriever) shouldBe Set.empty
    }

    "pass validation when calculated value is not equal to empty AC76 and AC77 is empty" in {
      when(boxRetriever.ac76()).thenReturn(AC76(None))
      when(boxRetriever.ac77()).thenReturn(AC77(None))
      AC190(Some(125)).validate(boxRetriever) shouldBe Set.empty
    }

    "calculate itself as None if AC187 is None" in {
      when(boxRetriever.ac187()).thenReturn(AC187(None))
      when(boxRetriever.ac77()).thenReturn(AC77(Some(1)))
      when(boxRetriever.ac189()).thenReturn(AC189(None))
      AC190.calculate(boxRetriever).value shouldEqual None
    }

    "calculate itself as None if AC187 is Some(false)" in {
      when(boxRetriever.ac187()).thenReturn(AC187(Some(false)))
      when(boxRetriever.ac77()).thenReturn(AC77(Some(1)))
      when(boxRetriever.ac189()).thenReturn(AC189(None))
      AC190.calculate(boxRetriever).value shouldEqual None
    }

    "calculate itself as Some(AC77 + AC189) if AC187 is Some(true)" in {
      when(boxRetriever.ac187()).thenReturn(AC187(Some(true)))
      when(boxRetriever.ac77()).thenReturn(AC77(Some(1)))
      when(boxRetriever.ac189()).thenReturn(AC189(Some(1)))
      AC190.calculate(boxRetriever).value shouldEqual Some(2)
    }
  }
}
