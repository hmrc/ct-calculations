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

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._


case class AC5052A(value: Option[Int]) extends CtBoxIdentifier(name = "Debtors due after more than one year") with CtOptionalInteger
                                                                                                              with Input
                                                                                                              with ValidatableBox[Frs102AccountsBoxRetriever]
                                                                                                              with SelfValidatableBox[Frs102AccountsBoxRetriever, Option[Int]]

with Validators {

  private def noteHasValue(boxRetriever: Frs102AccountsBoxRetriever): Boolean = {
    boxRetriever match {
      case x: AbridgedAccountsBoxRetriever =>
        x.ac5052A().hasValue ||
          x.ac5052B().hasValue ||
          x.ac5052C().hasValue

      case x: FullAccountsBoxRetriever =>
        x.ac134().hasValue ||
          x.ac135().hasValue ||
          x.ac138().hasValue ||
          x.ac139().hasValue ||
          x.ac136().hasValue ||
          x.ac137().hasValue ||
          x.ac140().hasValue ||
          x.ac141().hasValue ||
          x.ac5052A().hasValue ||
          x.ac5052B().hasValue ||
          x.ac5052C().hasValue
    }
  }

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors (
      failIf(boxRetriever.ac52().noValue && boxRetriever.ac53().noValue)(validateCannotExist(boxRetriever)),
      failIf(boxRetriever.ac52().hasValue || boxRetriever.ac53().hasValue)(validateNotEmpty(boxRetriever)),
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
