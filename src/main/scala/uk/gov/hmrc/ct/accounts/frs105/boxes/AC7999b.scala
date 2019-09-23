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




import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, CtValidation, Input, SelfValidatableBox}

case class AC7999b(value: Option[Boolean]) extends CtBoxIdentifier(name = "Company does not have off balance sheet arrangements")
  with CtOptionalBoolean
  with Input
  with SelfValidatableBox[Frs105AccountsBoxRetriever, Option[Boolean]] {

  override def validate(boxRetriever: Frs105AccountsBoxRetriever): Set[CtValidation] = {
    val otherButton = boxRetriever.ac7999a()
    collectErrors(
      requiredErrorIf((value.isEmpty || value.contains(false)) && otherButton.isFalse)
    )
  }
}
