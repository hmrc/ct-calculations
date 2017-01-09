/*
 * Copyright 2017 HM Revenue & Customs
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

import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever._


case class AC5052A(value: Option[Int]) extends CtBoxIdentifier(name = "Debtors due after more than one year") with CtOptionalInteger
                                                                                                              with Input
                                                                                                              with ValidatableBox[Frs102AccountsBoxRetriever]
                                                                                                              with SelfValidatableBox[Frs102AccountsBoxRetriever, Option[Int]]

with Validators {

  private def noteHasValue(boxRetriever: Frs102AccountsBoxRetriever): Boolean = {
    boxRetriever match {
      case x: AbridgedAccountsBoxRetriever => anyHaveValue(x.ac5052A(), x.ac5052B(), x.ac5052C())
      case x: FullAccountsBoxRetriever => anyHaveValue(x.ac134(), x.ac135(), x.ac138(), x.ac139(), x.ac136(), x.ac137(), x.ac140(), x.ac141(), x.ac5052A(), x.ac5052B(), x.ac5052C())
    }
  }

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    collectErrors (
      failIf(ac52().noValue && ac53().noValue)(
        validateCannotExist(boxRetriever)
      ),
      failIf(anyHaveValue(ac52(), ac53()))(
        validateNotEmpty(boxRetriever)
      ),
      validateMoney(value, min = 0),
      validateOptionalIntegerLessOrEqualBox(boxRetriever.ac52())
    )
  }

  private def validateCannotExist(boxRetriever: Frs102AccountsBoxRetriever)(): Set[CtValidation] = {
    if (noteHasValue(boxRetriever))
      Set(CtValidation(None, "error.balanceSheet.debtors.cannotExist"))
    else
      Set.empty
  }

  private def validateNotEmpty(boxRetriever: Frs102AccountsBoxRetriever)(): Set[CtValidation] = {
    if (!noteHasValue(boxRetriever))
      Set(CtValidation(None, "error.balanceSheet.debtors.mustNotBeEmpty"))
    else
      Set.empty
  }

}
