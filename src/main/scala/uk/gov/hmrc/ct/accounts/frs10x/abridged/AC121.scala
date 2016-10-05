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

import uk.gov.hmrc.ct.accounts.frs10x.abridged.calculations.IntangibleAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs10x.abridged.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC121(value: Option[Int]) extends CtBoxIdentifier(name = "Amortisation at [POA END]")
  with CtOptionalInteger
  with Input
  with ValidatableBox[AbridgedAccountsBoxRetriever]
  with Validators
  with Debit {

  override def validate(boxRetriever: AbridgedAccountsBoxRetriever): Set[CtValidation] = {

    collectErrors(
      validateMoney(value, min = 0)
    )
  }
}

object AC121 extends Calculated[AC121, AbridgedAccountsBoxRetriever]
  with IntangibleAssetsCalculator {

  override def calculate(boxRetriever: AbridgedAccountsBoxRetriever): AC121 = {
    import boxRetriever._
    calculateAC121(ac118(), ac119(), ac120(), ac211())
  }

}
