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

package uk.gov.hmrc.ct.accounts.frs10x.abridged.relatedPartyTransactions

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs10x.{AccountsFreeTextValidationFixture, MockAbridgedAccountsRetriever}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.ValidatableBox._

class AC7802Spec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfter
  with MockAbridgedAccountsRetriever with AccountsFreeTextValidationFixture {

  testMandatoryAccountsCharacterLimitValidation("AC7802", StandardCohoNameFieldLimit, AC7802)
  testAccountsCoHoNameFieldValidation("AC7802", AC7802)

  "AC7802" should {
    "be mandatory" in {
      AC7802(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7802"), "error.AC7802.required", None))
    }
  }
}
