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

package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.offPayRollWorking.DeductionCannotBeGreaterThanProfit

case class AC403(value: Option[Int]) extends CtBoxIdentifier(name = "Current Deductions from OPW")
  with CtOptionalInteger
  with Input
  with Debit
  with ValidatableBox[AccountsBoxRetriever] {
  override def validate(boxRetriever: AccountsBoxRetriever): Set[CtValidation] = {
    val ac401 = boxRetriever.ac401()
    collectErrors(
      failIf(ac401.value.isDefined && value.isEmpty){
        Set(CtValidation(Some("AC403"), "error.AC403.required"))
      },
      DeductionCannotBeGreaterThanProfit(boxRetriever.ac401(), this)
    )
  }
}

object AC403 {
  def apply(value: Int): AC403 = AC403(Some(value))
}
