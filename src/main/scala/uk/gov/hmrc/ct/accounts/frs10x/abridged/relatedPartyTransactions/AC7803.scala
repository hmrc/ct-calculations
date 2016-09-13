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

package uk.gov.hmrc.ct.accounts.frs10x.abridged.relatedPartyTransactions

import uk.gov.hmrc.ct.accounts.frs10x.abridged.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC7803(value: Option[String]) extends CtBoxIdentifier(name = "Name of related party")
  with CtOptionalString
  with Input
  with ValidatableBox[AbridgedAccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: AbridgedAccountsBoxRetriever): Set[CtValidation] = {

    collectErrors(
      validateAsMandatory(this),
      validateStringMaxLength("AC7803", value.getOrElse(""), StandardCohoTextFieldLimit),
      validateCoHoOptionalString("AC7803", this)
    )
  }
}