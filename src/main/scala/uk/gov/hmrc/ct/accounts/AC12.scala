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

package uk.gov.hmrc.ct.accounts

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.utils.DateImplicits._


case class AC12(value: Option[Int]) extends CtBoxIdentifier(name = "Current Turnover/Sales")
  with CtOptionalInteger
  with Input
  with ValidatableBox[AccountsBoxRetriever with FilingAttributesBoxValueRetriever]
  with Validators  {

  override def validate(boxRetriever: AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    failIf(isFrs102HmrcAbridgedReturnWithLongPoA(boxRetriever)) {
      collectErrors(
        validateMoney(value, min = 0),
        validateAsMandatory(this)
      )
    }
  }

  private def isFrs102HmrcAbridgedReturnWithLongPoA(boxRetriever: AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Boolean = {
    boxRetriever.hmrcFiling().value &&
    boxRetriever.abridgedFiling().value &&
    isFRS102(boxRetriever) &&
    isLongPoA(boxRetriever)
  }

  private def isFRS102(boxRetriever: AccountsBoxRetriever): Boolean = {
    boxRetriever.ac3().value >= new LocalDate(2016, 1, 1)
  }

  private def isLongPoA(boxRetriever: AccountsBoxRetriever): Boolean = {
    boxRetriever.ac3().value.plusMonths(12) <= boxRetriever.ac4().value
  }

}

object AC12 {
  def apply(value: Int): AC12 = AC12(Some(value))
}
