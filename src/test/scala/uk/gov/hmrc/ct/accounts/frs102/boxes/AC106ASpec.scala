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

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito._
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.ValidatableBox._

class AC106ASpec extends WordSpec with Matchers with MockFrs102AccountsRetriever with AccountsFreeTextValidationFixture[Frs102AccountsBoxRetriever] {

  override def setUpMocks(): Unit = {
    when(boxRetriever.ac7300()).thenReturn(AC7300(Some(true)))
  }

  testTextFieldValidation("AC106A", AC106A, testUpperLimit = Some(StandardCohoTextFieldLimit))
  testTextFieldIllegalCharacterValidationReturnsIllegalCharacters("AC106A", AC106A)

  "AC106A" should {

    "not validate with any errors when AC7300 is true and AC106A has a value" in {

      when(boxRetriever.ac7300()).thenReturn(AC7300(Some(true)))

      AC106A(Some("Have employed Chuck Norris as CEO")).validate(boxRetriever) shouldBe empty
    }

    "not validate with any errors when AC7300 is true and AC106A has no value" in {

      when(boxRetriever.ac7300()).thenReturn(AC7300(Some(true)))

      AC106A(None).validate(boxRetriever) shouldBe empty
    }

    "not validate with any errors when AC7300 is false and AC106A has no value" in {

      when(boxRetriever.ac7300()).thenReturn(AC7300(Some(false)))

      AC106A(None).validate(boxRetriever) shouldBe empty
    }

    "not validate with any errors when AC7300 is None and AC106A has no value" in {
      when(boxRetriever.ac7300()).thenReturn(AC7300(None))

      AC106A(None).validate(boxRetriever) shouldBe empty
    }

    "validate with should not exist error when AC7300 is None and AC106A has a value" in {
      when(boxRetriever.ac7300()).thenReturn(AC7300(None))

      AC106A(Some("Employed Steven Segal as CEO")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC106A"), "error.AC106A.cannot.exist"))
    }

    "validate with should not exist error when AC7300 is false and AC106A has a value" in {
      when(boxRetriever.ac7300()).thenReturn(AC7300(Some(false)))

      AC106A(Some("Employed Jean Claude Van Damme as CEO")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC106A"), "error.AC106A.cannot.exist"))
    }

  }
}
