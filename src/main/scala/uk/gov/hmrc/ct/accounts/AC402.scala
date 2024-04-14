/*
 * Copyright 2024 HM Revenue & Customs
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

case class AC402(value: Option[Int]) extends CtBoxIdentifier(name = " Previous Gross turnover from OPW")
with CtOptionalInteger
with Input
  with ValidatableBox[AccountsBoxRetriever] {
  override def validate(boxRetriever: AccountsBoxRetriever): Set[CtValidation] = {
    val ac404 = boxRetriever.ac404()

    collectErrors(
      failIf(ac404.value.isDefined && value.isEmpty){
        Set(CtValidation(Some("AC402"), "error.AC402.required"))
      },
      exceedsMax(value,999999),
      validateZeroOrPositiveInteger(this)
    )
  }
}

object AC402 {
  def apply(value: Int): AC402 = AC402(Some(value))
}