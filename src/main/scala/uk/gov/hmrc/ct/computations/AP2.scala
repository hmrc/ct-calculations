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

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class AP2(inputValue: Option[Int], defaultValue: Option[Int]) extends CtBoxIdentifier(name = "Turnover apportioned during accounting period") with CtOptionalInteger with InputWithDefault[Int] with ValidatableBox[AccountsBoxRetriever with ComputationsBoxRetriever] with CtTypeConverters {
  override def validate(boxRetriever: AccountsBoxRetriever with ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateZeroOrPositiveInteger(this),
      totalErrors(boxRetriever)
    )
  }

  private def totalErrors(boxRetriever: AccountsBoxRetriever with ComputationsBoxRetriever) = {
    failIf(boxRetriever.ap1() + inputValue.getOrElse(0) + boxRetriever.ap3() != (boxRetriever.ac12().value.getOrElse(0) + boxRetriever.ac401().value.getOrElse(0) - boxRetriever.ac403().value.getOrElse(0))) {
      Set(CtValidation(None, "error.apportionmentTurnover.total"))
    }
  }
}

object AP2 {

  def apply(inputValue: Option[Int]): AP2 = AP2(inputValue = inputValue, defaultValue = None)
}
