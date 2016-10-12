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
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.{AccountsFreeTextValidationFixture, MockAbridgedAccountsRetriever}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.ValidatableBox._

class AC5052BSpec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfter
  with MockAbridgedAccountsRetriever with AccountsFreeTextValidationFixture {

  testAccountsCharacterLimitValidation("AC5052B", StandardCohoTextFieldLimit, AC5052B)
  testAccountsCoHoTextFieldValidation("AC5052B", AC5052B)

  before {
    when(boxRetriever.ac52).thenReturn(AC52(Some(33)))
  }

  "AC5052B" should {

    "fail validation when populated and AC52 is empty" in {
      when(boxRetriever.ac52()).thenReturn(AC52(None))
      AC5052B(Some("test text")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC5052B"), "error.AC5052B.cannot.exist"))
    }
  }
}