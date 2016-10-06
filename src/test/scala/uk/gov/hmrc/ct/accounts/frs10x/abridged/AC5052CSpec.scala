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

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.AC205
import uk.gov.hmrc.ct.accounts.frs10x.{AccountsMoneyValidationFixture, MockAbridgedAccountsRetriever}
import uk.gov.hmrc.ct.box.CtValidation

class AC5052CSpec extends WordSpec with MockitoSugar with Matchers with MockAbridgedAccountsRetriever with AccountsMoneyValidationFixture with BeforeAndAfter {

  before {
    when(boxRetriever.ac52).thenReturn(AC52(Some(STANDARD_MAX + 1)))
    when(boxRetriever.ac53).thenReturn(AC53(Some(STANDARD_MAX + 1)))
  }

  testAccountsMoneyValidationWithMin("AC5052C", minValue = 0, AC5052C)

  "fail validation when populated and AC52 is empty" in {
    when(boxRetriever.ac52()).thenReturn(AC52(None))
    AC5052C(Some(4)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC5052C"), "error.AC5052C.cannot.exist"))
  }

  "cannot be populated if the previous year date is not set" in {
    when(boxRetriever.ac52()).thenReturn(AC52(Some(123)))
    when(boxRetriever.ac205()).thenReturn(AC205(None))
    AC5052C(Some(4)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC5052C"), "error.AC5052C.cannot.exist"))
  }

  "pass the validation if AC52 and AC205 are set" in {
    when(boxRetriever.ac52()).thenReturn(AC52(Some(123)))
    when(boxRetriever.ac205()).thenReturn(AC205(Some(LocalDate.parse("2016-01-01"))))
    AC5052C(Some(4)).validate(boxRetriever) shouldBe Set.empty
  }

  "fail validation when greater than AC53" in {
    when(boxRetriever.ac52()).thenReturn(AC52(Some(123)))
    when(boxRetriever.ac53()).thenReturn(AC53(Some(30)))
    AC5052C(Some(35)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC5052C"), "error.AC5052C.mustBeLessOrEqual.AC53"))
  }

  "pass validation when equals AC52" in {
    when(boxRetriever.ac52()).thenReturn(AC52(Some(123)))
    when(boxRetriever.ac53()).thenReturn(AC53(Some(30)))
    AC5052C(Some(30)).validate(boxRetriever) shouldBe Set.empty
  }

  "pass validation when less than AC52" in {
    when(boxRetriever.ac52()).thenReturn(AC52(Some(123)))
    when(boxRetriever.ac53()).thenReturn(AC53(Some(30)))
    AC5052C(Some(25)).validate(boxRetriever) shouldBe Set.empty
  }
 }
