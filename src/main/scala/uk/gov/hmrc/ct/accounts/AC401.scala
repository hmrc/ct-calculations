/*
 * Copyright 2020 HM Revenue & Customs
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

case class AC401(value: Option[Int]) extends CtBoxIdentifier(name = "Current Gross turnover from OPW")
with CtOptionalInteger
with Input
with ValidatableBox[AccountsBoxRetriever] {
  override def validate(boxRetriever: AccountsBoxRetriever): Set[CtValidation] = {
    val cato24 = boxRetriever.cato24

    collectErrors(
      failIf(cato24.isTrue && value.isEmpty){
        Set(CtValidation(Some("AC401"), "error.AC401.required"))
      },
      exceedsMax(value, 999999),
      validateZeroOrPositiveInteger(this)
    )

  }
}

object AC401 {
  def apply(value: Int): AC401 = AC401(Some(value))
}