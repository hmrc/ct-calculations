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
import uk.gov.hmrc.ct.accounts.MockFrs102AccountsRetriever
import uk.gov.hmrc.ct.accounts.frs102.AccountsFreeTextValidationFixture
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.ValidatableBox._

class AC5058ASpec extends WordSpec
  with MockitoSugar
  with Matchers
  with MockFrs102AccountsRetriever
  with AccountsFreeTextValidationFixture {

  override def setUpMocks(): Unit = {
    when(boxRetriever.ac58()).thenReturn(AC58(Some(100)))
  }

  testAccountsCharacterLimitValidation("AC5058A", StandardCohoTextFieldLimit, AC5058A)
  testAccountsCoHoTextFieldValidation("AC5058A", AC5058A)

  "AC5058A" should {
    "fail validation when populated and AC58 is empty" in {
      when(boxRetriever.ac58()).thenReturn(AC58(None))
      AC5058A(Some("testing")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC5058A"), "error.AC5058A.cannot.exist"))
    }
  }
}
