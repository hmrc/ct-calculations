/*
 * Copyright 2021 HM Revenue & Customs
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
import uk.gov.hmrc.ct.box.ValidatableBox.StandardCohoTextFieldLimit
import uk.gov.hmrc.ct.box.{
  CtBoxIdentifier,
  CtOptionalString,
  CtValidation,
  Input,
  SelfValidatableBox
}

case class AC200(value: Option[String])
    extends CtBoxIdentifier(name = "OffAC107Spec balance sheet disclosure note")
    with CtOptionalString
    with Input
    with SelfValidatableBox[Frs102AccountsBoxRetriever, Option[String]] {

  def validateAgainstAC200A(boxRetriever: Frs102AccountsBoxRetriever,
                            boxId: String,
                            ac200value: Option[String]): Set[CtValidation] = {
    (boxRetriever.ac200a(), ac200value) match {
      case (AC200A(Some(true)), None) =>
        validateStringAsMandatory(boxId, AC200(ac200value))
      case (AC200A(Some(true)), Some("")) =>
        validateStringAsMandatory(boxId, AC200(ac200value))
      case (AC200A(Some(true)), Some(ac200value))
          if ac200value.startsWith(" ") =>
        validateStringAsMandatoryWithNoTrailingWhitespace(
          boxId,
          AC200(Some(ac200value)))
      case (_, _) => Set()
    }
  }

  override def validate(
      boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateAgainstAC200A(boxRetriever, this.boxId, value),
      validateOptionalStringByLength(1, StandardCohoTextFieldLimit)
    )
  }
}
