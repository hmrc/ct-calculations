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

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC8082(value: Option[Boolean]) extends CtBoxIdentifier(name = "The members have not required the company to obtain an audit in accordance with section 476 of the Companies Act 2006.")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[Frs10xAccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs10xAccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateAsMandatory(this)
    )
  }
  
}
