/*
 * Copyright 2019 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs105.boxes

import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AccountsIntegerValidationFixture, MockFrs105AccountsRetriever}

class AC7998Spec extends WordSpec with Matchers with MockitoSugar with AccountsIntegerValidationFixture[Frs105AccountsBoxRetriever] with MockFrs105AccountsRetriever {

  private val boxID = "AC7998"
  private val minNumberOfEmployees = Some(1)
  private val maxNumberOfEmployees = Some(99999)
  private val isMandatory = Some(true)

  testIntegerFieldValidation(boxID, AC7998, minNumberOfEmployees, maxNumberOfEmployees, isMandatory)
}


