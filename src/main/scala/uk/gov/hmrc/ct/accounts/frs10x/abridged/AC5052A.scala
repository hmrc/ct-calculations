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

package uk.gov.hmrc.ct.accounts.frs10x.abridged

import uk.gov.hmrc.ct.accounts.AccountsMoneyValidation
import uk.gov.hmrc.ct.accounts.frs10x.abridged.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.box._


case class AC5052A(value: Option[Int]) extends CtBoxIdentifier(name = "Debtors due after more than one year") with CtOptionalInteger
                                                                                                              with Input
                                                                                                              with ValidatableBox[AbridgedAccountsBoxRetriever]
                                                                                                              with AccountsMoneyValidation {

  override def validate(boxRetriever: AbridgedAccountsBoxRetriever): Set[CtValidation] = {
    (boxRetriever.ac52, value) match {
      case (AC52(None), Some(n)) => Set(CtValidation(Some("AC5052A"), "error.AC5052A.cannot.exist"))
      case (AC52(Some(_)), Some(n)) => validateMoney("AC5052A", min = 0)
      case _ => Set.empty
    }
  }
}

