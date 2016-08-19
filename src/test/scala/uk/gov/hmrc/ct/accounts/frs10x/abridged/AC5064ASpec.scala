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
import uk.gov.hmrc.ct.accounts.frs10x.{AccountsFreeTextValidationFixture, MockAbridgedAccountsRetriever}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.ValidatableBox._

class AC5064ASpec extends WordSpec
  with MockitoSugar
  with Matchers
  with MockAbridgedAccountsRetriever
  with AccountsFreeTextValidationFixture {

  override def setUpMocks(): Unit = {
    when(boxRetriever.ac64()).thenReturn(AC64(Some(100)))
  }

  testAccountsCharacterLimitValidation("AC5064A", StandardCohoTextfieldLimit, AC5064A)
  testAccountsRegexValidation("AC5064A", AC5064A)

  "AC5064A" should {
    "fail validation when populated and AC64 is empty" in {
      when(boxRetriever.ac64()).thenReturn(AC64(None))
      AC5064A(Some("testing")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC5064A"), "error.AC5064A.cannot.exist"))
    }
  }
}
