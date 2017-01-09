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
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.ValidatableBox._

class AC5058ASpec extends WordSpec
  with MockitoSugar
  with Matchers
  with MockFrs102AccountsRetriever
  with AccountsFreeTextValidationFixture[Frs102AccountsBoxRetriever] {

  override def setUpMocks(): Unit = {
    when(boxRetriever.ac58()).thenReturn(AC58(Some(100)))
  }

  testTextFieldValidation("AC5058A", AC5058A, testUpperLimit = Some(StandardCohoTextFieldLimit))
  testTextFieldIllegalCharacterValidationReturnsIllegalCharacters("AC5058A", AC5058A)

  "AC5058A" should {
    "throw cannot exist error when populated and AC58 and AC59 are empty in Abridged" in {
      val boxRetriever = mock[AbridgedAccountsBoxRetriever]
      when(boxRetriever.ac58()).thenReturn(AC58(None))
      when(boxRetriever.ac59()).thenReturn(AC59(None))
      AC5058A(Some("testing")).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.creditorsWithinOneYear.cannotExist"))
    }

    "have no errors when not populated and AC58 is empty in Abridged" in {
      val boxRetriever = mock[AbridgedAccountsBoxRetriever]
      when(boxRetriever.ac58()).thenReturn(AC58(None))
      when(boxRetriever.ac59()).thenReturn(AC59(None))
      AC5058A(None).validate(boxRetriever) shouldBe empty
    }

    "have no errors when populated and AC58 is populated in Abridged" in {
      when(boxRetriever.ac58()).thenReturn(AC58(Some(10)))
      when(boxRetriever.ac59()).thenReturn(AC59(None))
      AC5058A(Some("Tasty note")).validate(boxRetriever) shouldBe empty
    }

    "have no errors when populated and AC59 is populated in Abridged" in {
      val boxRetriever = mock[AbridgedAccountsBoxRetriever]
      when(boxRetriever.ac58()).thenReturn(AC58(None))
      when(boxRetriever.ac59()).thenReturn(AC59(Some(10)))
      AC5058A(Some("Tasty note")).validate(boxRetriever) shouldBe empty
    }

    "have cannot exist error if Full accounts and no value in AC58 and any Creditors Within One Year box has a value" in {
      val boxRetriever = mock[FullAccountsBoxRetriever]

      when(boxRetriever.ac58()).thenReturn(AC58(None))
      when(boxRetriever.ac59()).thenReturn(AC59(None))
      when(boxRetriever.ac142()).thenReturn(AC142(None))
      when(boxRetriever.ac143()).thenReturn(AC143(None))
      when(boxRetriever.ac144()).thenReturn(AC144(None))
      when(boxRetriever.ac145()).thenReturn(AC145(None))
      when(boxRetriever.ac146()).thenReturn(AC146(None))
      when(boxRetriever.ac147()).thenReturn(AC147(None))
      when(boxRetriever.ac148()).thenReturn(AC148(None))
      when(boxRetriever.ac149()).thenReturn(AC149(None))
      when(boxRetriever.ac150()).thenReturn(AC150(None))
      when(boxRetriever.ac151()).thenReturn(AC151(None))
      when(boxRetriever.ac152()).thenReturn(AC152(None))
      when(boxRetriever.ac153()).thenReturn(AC153(None))
      AC5058A(Some("A note, yo")).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.creditorsWithinOneYear.cannotExist"))
    }

    "have no errors if Full accounts and any Creditors Within One Year box has a value" in {
      val boxRetriever = mock[FullAccountsBoxRetriever]

      when(boxRetriever.ac58()).thenReturn(AC58(Some(10)))
      when(boxRetriever.ac59()).thenReturn(AC59(None))
      when(boxRetriever.ac142()).thenReturn(AC142(None))
      when(boxRetriever.ac143()).thenReturn(AC143(None))
      when(boxRetriever.ac144()).thenReturn(AC144(None))
      when(boxRetriever.ac145()).thenReturn(AC145(None))
      when(boxRetriever.ac146()).thenReturn(AC146(None))
      when(boxRetriever.ac147()).thenReturn(AC147(None))
      when(boxRetriever.ac148()).thenReturn(AC148(None))
      when(boxRetriever.ac149()).thenReturn(AC149(None))
      when(boxRetriever.ac150()).thenReturn(AC150(None))
      when(boxRetriever.ac151()).thenReturn(AC151(None))
      when(boxRetriever.ac152()).thenReturn(AC152(None))
      when(boxRetriever.ac153()).thenReturn(AC153(None))
      AC5058A(Some("A note, yo")).validate(boxRetriever) shouldBe empty
    }

    "return an error if Full accounts and no Creditors Within One Year box has a value" in {
      val boxRetriever = mock[FullAccountsBoxRetriever]

      when(boxRetriever.ac58()).thenReturn(AC58(None))
      when(boxRetriever.ac59()).thenReturn(AC59(Some(10)))
      when(boxRetriever.ac142()).thenReturn(AC142(None))
      when(boxRetriever.ac143()).thenReturn(AC143(None))
      when(boxRetriever.ac144()).thenReturn(AC144(None))
      when(boxRetriever.ac145()).thenReturn(AC145(None))
      when(boxRetriever.ac146()).thenReturn(AC146(None))
      when(boxRetriever.ac147()).thenReturn(AC147(None))
      when(boxRetriever.ac148()).thenReturn(AC148(None))
      when(boxRetriever.ac149()).thenReturn(AC149(None))
      when(boxRetriever.ac150()).thenReturn(AC150(None))
      when(boxRetriever.ac151()).thenReturn(AC151(None))
      when(boxRetriever.ac152()).thenReturn(AC152(None))
      when(boxRetriever.ac153()).thenReturn(AC153(None))
      AC5058A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.creditors.within.one.year.note.one.box.required"))
    }

    "return no error if no Balance Sheet figures if Full accounts and no Creditors Within One Year box has a value" in {
      val boxRetriever = mock[FullAccountsBoxRetriever]

      when(boxRetriever.ac58()).thenReturn(AC58(None))
      when(boxRetriever.ac59()).thenReturn(AC59(None))
      when(boxRetriever.ac142()).thenReturn(AC142(None))
      when(boxRetriever.ac143()).thenReturn(AC143(None))
      when(boxRetriever.ac144()).thenReturn(AC144(None))
      when(boxRetriever.ac145()).thenReturn(AC145(None))
      when(boxRetriever.ac146()).thenReturn(AC146(None))
      when(boxRetriever.ac147()).thenReturn(AC147(None))
      when(boxRetriever.ac148()).thenReturn(AC148(None))
      when(boxRetriever.ac149()).thenReturn(AC149(None))
      when(boxRetriever.ac150()).thenReturn(AC150(None))
      when(boxRetriever.ac151()).thenReturn(AC151(None))
      when(boxRetriever.ac152()).thenReturn(AC152(None))
      when(boxRetriever.ac153()).thenReturn(AC153(None))
      AC5058A(None).validate(boxRetriever) shouldBe Set.empty
    }

    "return no mandatory errors for abridged accounts" in {
      val boxRetriever = mock[AbridgedAccountsBoxRetriever]
      when(boxRetriever.ac58()).thenReturn(AC58(Some(10)))
      when(boxRetriever.ac59()).thenReturn(AC59(Some(10)))
      AC5058A(None).validate(boxRetriever) shouldBe empty
    }
  }
}
