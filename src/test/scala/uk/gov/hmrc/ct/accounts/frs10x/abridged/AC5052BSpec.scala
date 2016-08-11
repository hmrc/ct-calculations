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
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs10x.{AccountsFreeTextValidationFixture, MockAbridgedAccountsRetriever}
import uk.gov.hmrc.ct.box.CtValidation

class AC5052BSpec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfter
  with MockAbridgedAccountsRetriever with AccountsFreeTextValidationFixture {

  testAccountsCharacterLimitValidation("AC5052B", 20000, AC5052B)
  testAccountsRegexValidation("AC5052B", AC5052B)

  before {
    when(boxRetriever.ac52).thenReturn(AC52(Some(33)))
  }

  "AC5052B" should {

    "returns AC52 required validation error when AC5052B populated and AC52 is empty" in {
      when(boxRetriever.ac52()).thenReturn(AC52(None))
      AC5052B(Some("text")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC52"), "error.AC52.required"))
    }
  }
}
