/*
 * Copyright 2023 HM Revenue & Customs
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
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, CtValidation, Input, SelfValidatableBox}

case class AC200A(value: Option[Boolean])
extends CtBoxIdentifier(name = "Company does have off balance sheet arrangements")
  with CtOptionalBoolean
  with Input
  with SelfValidatableBox[Frs102AccountsBoxRetriever, Option[Boolean]] {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      failIf(value.isEmpty) {
        validateAsMandatory()
      }
    )
  }
  }