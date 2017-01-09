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

case class AC106A(value: Option[String]) extends CtBoxIdentifier(name = "Employees note additional information")
  with CtOptionalString
  with Input
  with SelfValidatableBox[Frs102AccountsBoxRetriever, Option[String]]
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    val noteSelectedForInclusion = boxRetriever.ac7300().orFalse

    collectErrors (
      cannotExistIf(!noteSelectedForInclusion && value.nonEmpty),
      validateStringMaxLength(value.getOrElse(""), StandardCohoTextFieldLimit),
      validateCoHoStringReturnIllegalChars()
    )
  }
}
