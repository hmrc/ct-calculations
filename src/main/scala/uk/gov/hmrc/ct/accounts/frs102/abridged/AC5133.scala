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

package uk.gov.hmrc.ct.accounts.frs102.abridged

import uk.gov.hmrc.ct.accounts.frs102.abridged.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC5133(value: Option[String]) extends CtBoxIdentifier(name = "Tangible assets additional information") with CtOptionalString
with Input
with ValidatableBox[AbridgedAccountsBoxRetriever]
with Validators {

  override def validate(boxRetriever: AbridgedAccountsBoxRetriever): Set[CtValidation] = {
    collectErrors (
      failIf (boxRetriever.ac44().value.nonEmpty) (
        collectErrors (
          validateOptionalStringByLength("AC5133", this, 1, StandardCohoTextFieldLimit),
          validateCoHoOptionalString("AC5133", this)
        )
      )
    )
  }
}
