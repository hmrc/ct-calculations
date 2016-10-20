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

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class ACQ5031(value: Option[Boolean]) extends CtBoxIdentifier(name = "Land and buildings")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[FullAccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: FullAccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._
    collectErrors(
      cannotExistIf(ac44.noValue && ac45.noValue),

      failIf(ac44.hasValue || ac45.hasValue) {
        atLeastOneBoxHasValue("balanche.sheet.tangible.assets", this, acq5032, acq5033, acq5034, acq5035)
      }
    )
  }
}
