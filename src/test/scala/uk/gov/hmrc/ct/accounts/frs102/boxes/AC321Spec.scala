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
import uk.gov.hmrc.ct.accounts.frs102.{AccountsFreeTextValidationFixture, MockAbridgedAccountsRetriever}
import uk.gov.hmrc.ct.box.ValidatableBox._

class AC321Spec extends WordSpec
  with MockitoSugar
  with Matchers
  with MockAbridgedAccountsRetriever
  with AccountsFreeTextValidationFixture {

  override def setUpMocks(): Unit = {
    when(boxRetriever.ac321()).thenReturn(AC321(Some("text")))
  }

  testAccountsCharacterLimitValidation("AC321", StandardCohoTextFieldLimit, AC321)
  testAccountsCoHoTextFieldValidation("AC321", AC321)

}