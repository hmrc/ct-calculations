/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.approval.accountsApproval.accountsApproval

import org.scalatest.BeforeAndAfter
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.accounts.approval.boxes.AC8092
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockAccountsRetriever}
import uk.gov.hmrc.ct.box.ValidatableBox._

class AC8092Spec extends AnyWordSpec with MockitoSugar with Matchers with BeforeAndAfter
  with MockAccountsRetriever with AccountsFreeTextValidationFixture[AccountsBoxRetriever] {

  testTextFieldValidation("AC8092", AC8092, testUpperLimit = Some(StandardCohoNameFieldLimit))
  testTextFieldIllegalCharactersValidation("AC8092", AC8092)

}
