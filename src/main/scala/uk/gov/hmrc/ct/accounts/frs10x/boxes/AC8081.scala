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

import uk.gov.hmrc.ct.accounts.frs10x.retriever.{Frs10xAccountsBoxRetriever, Frs10xDormancyBoxRetriever}
import uk.gov.hmrc.ct.box._

case class AC8081(value: Option[Boolean]) extends CtBoxIdentifier(name = "For the year ending <<POA END DATE>> the company was entitled to exemption under section 477 of the Companies Act 2006 relating to small companies.")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[Frs10xAccountsBoxRetriever with Frs10xDormancyBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs10xAccountsBoxRetriever with Frs10xDormancyBoxRetriever): Set[CtValidation] = {
    failIf(!boxRetriever.acq8999().orFalse) (
      validateBooleanAsTrue("AC8081", this)
    )
  }

}
