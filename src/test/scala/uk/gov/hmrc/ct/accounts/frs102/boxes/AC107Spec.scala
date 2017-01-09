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
import uk.gov.hmrc.ct.accounts.{MockFrs102AccountsRetriever, AccountsMoneyValidationFixture, AC205}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC107Spec extends AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with MockFrs102AccountsRetriever {

  override def setUpMocks() = {
    super.setUpMocks()
    when(boxRetriever.ac7300()).thenReturn(AC7300(Some(true)))
  }

  testAccountsMoneyValidationWithMinMax("AC107", 0, 99999, AC107.apply, testEmpty = false)

  "AC107" should {

    "have no errors if blank and  AC7300 is true" in {

      when(boxRetriever.ac7300()).thenReturn(AC7300(Some(true)))

      AC107(None).validate(boxRetriever) shouldBe empty
    }

    "not validate with any errors when AC7300 is true and AC107 has a value" in {

      when(boxRetriever.ac7300()).thenReturn(AC7300(Some(true)))

      AC107(Some(10)).validate(boxRetriever) shouldBe empty
    }

    "not validate with any errors when AC7300 is false and AC107 has no value" in {

      when(boxRetriever.ac7300()).thenReturn(AC7300(Some(false)))

      AC107(None).validate(boxRetriever) shouldBe empty
    }

    "not validate with any errors when AC7300 is None and AC107 has no value" in {
      when(boxRetriever.ac7300()).thenReturn(AC7300(None))

      AC107(None).validate(boxRetriever) shouldBe empty
    }

    "validate with should return exist error when AC7300 is None and AC107 has a value" in {
      when(boxRetriever.ac7300()).thenReturn(AC7300(None))

      AC107(Some(100)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC107"), "error.AC107.cannot.exist"))
    }

    "validate with should return exist error when AC7300 is false and AC107 has a value" in {
      when(boxRetriever.ac7300()).thenReturn(AC7300(Some(false)))

      AC107(Some(100)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC107"), "error.AC107.cannot.exist"))
    }

    "validate with should return exist error when AC7300 is true, AC107 has a value and Previous PoA is empty" in {
      when(boxRetriever.ac205()).thenReturn(AC205(None))
      when(boxRetriever.ac7300()).thenReturn(AC7300(Some(false)))

      AC107(Some(100)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC107"), "error.AC107.cannot.exist"))
    }
  }

}
