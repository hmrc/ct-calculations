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

package uk.gov.hmrc.ct.accounts.frs102.abridged

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.abridged.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs102.{AccountsFreeTextValidationFixture, MockAbridgedAccountsRetriever}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

class AC320Spec extends WordSpec
  with MockitoSugar
  with Matchers
  with MockAbridgedAccountsRetriever
  with AccountsFreeTextValidationFixture {

  override def setUpMocks(): Unit = {
    when(boxRetriever.ac320A()).thenReturn(AC320A(Some("text")))
  }

  "AC320 validate" should {
    "return errors when AC320 is empty" in {
      val mockBoxRetriever = mock[AbridgedAccountsBoxRetriever]

      AC320(None).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("AC320"), "error.AC320.required"))
    }
  }

  "return value when AC320 is not empty" in {
    val mockBoxRetriever = mock[AbridgedAccountsBoxRetriever]

    AC320(Some(true)).value shouldBe Some(true)
  }
}
