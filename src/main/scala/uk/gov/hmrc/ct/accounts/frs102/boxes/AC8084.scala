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

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class AC8084(value: Option[Boolean]) extends CtBoxIdentifier(name = "The members have agreed to the preparation of abridged accounts for this accounting period in accordance with Section 444(2A).")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever]
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    collectErrors(
      failIf(boxRetriever.abridgedFiling().value)(validateAsMandatory(this))
    )
  }
  
}
