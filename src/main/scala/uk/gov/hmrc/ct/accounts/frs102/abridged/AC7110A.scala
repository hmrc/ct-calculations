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
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.ValidatableBox._

case class AC7110A(value: Option[String]) extends CtBoxIdentifier(name = "Other accounting policies")
                                          with CtOptionalString
                                          with Input
                                          with ValidatableBox[AbridgedAccountsBoxRetriever]
                                          with Validators {


  override def validate(boxRetriever: AbridgedAccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateStringMaxLength("AC7110A", value.getOrElse(""), StandardCohoTextFieldLimit),
      validateCoHoOptionalString("AC7110A", this)
    )
  }
}
