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
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC7401(value: Option[String]) extends CtBoxIdentifier(name = "Financial commitments") with CtOptionalString
with Input
with ValidatableBox[Frs102AccountsBoxRetriever]
with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors (
      cannotExistIf(!boxRetriever.ac7400().orFalse && value.nonEmpty),

      failIf (boxRetriever.ac7400().orFalse) (
        collectErrors (
          validateStringAsMandatory("AC7401", this),
          validateOptionalStringByLength("AC7401", this, 1, StandardCohoTextFieldLimit),
          validateCoHoStringReturnIllegalChars("AC7401", this)
        )
      )
    )
  }
}
