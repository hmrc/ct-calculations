/*
 * Copyright 2023 HM Revenue & Customs
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

import uk.gov.hmrc.ct.accounts.frs102.calculations.IntangibleAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._

case class AC121(value: Option[Int]) extends CtBoxIdentifier(name = "Amortisation at [POA END]")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs102AccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {

    collectErrors(
      validateMoney(value, min = 0)
    )
  }
}

object AC121 extends Calculated[AC121, Frs102AccountsBoxRetriever]
  with IntangibleAssetsCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC121 = {
    boxRetriever match {
      case x: AbridgedAccountsBoxRetriever => calculateAbridgedAC121(x.ac118(), x.ac119(), x.ac120(), x.ac211())
      case x: FullAccountsBoxRetriever => calculateFullAC121(x.ac121A(), x.ac121B())
    }
  }

}
