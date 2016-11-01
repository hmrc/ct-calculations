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
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC5064A(value: Option[String]) extends CtBoxIdentifier(name = "Balance sheet - Creditors after 1 year note.")
                                          with CtOptionalString
                                          with Input
                                          with ValidatableBox[Frs102AccountsBoxRetriever] {

  private def fullNoteHasValue(boxRetriever: FullAccountsBoxRetriever): Boolean = {
    boxRetriever.ac156().hasValue ||
      boxRetriever.ac157().hasValue ||
      boxRetriever.ac158().hasValue ||
      boxRetriever.ac159().hasValue ||
      boxRetriever.ac160().hasValue ||
      boxRetriever.ac161().hasValue ||
      boxRetriever.ac162().hasValue ||
      boxRetriever.ac163().hasValue ||
      boxRetriever.ac5064A().hasValue
  }

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors (
      failIf(!boxRetriever.ac64().hasValue)(validateCannotExist(boxRetriever)),
      failIf(boxRetriever.ac64().hasValue)(validateNoteIsMandatory(boxRetriever)),
      validateStringMaxLength("AC5064A", value.getOrElse(""), StandardCohoTextFieldLimit),
      validateCoHoOptionalString("AC5064A", this)
    )
  }

  private def validateCannotExist(boxRetriever: Frs102AccountsBoxRetriever)(): Set[CtValidation] = {
    boxRetriever match {
      case x: AbridgedAccountsBoxRetriever =>
        if (hasValue)
          Set(CtValidation(None, "error.balanceSheet.creditorsAfterOneYear.cannotExist"))
        else
          Set.empty

      case x: FullAccountsBoxRetriever =>
        if (fullNoteHasValue(x))
          Set(CtValidation(None, "error.balanceSheet.creditorsAfterOneYear.cannotExist"))
        else
          Set.empty
    }
  }

  private def validateNoteIsMandatory(boxRetriever: Frs102AccountsBoxRetriever)(): Set[CtValidation] = {
    boxRetriever match {
      case x: FullAccountsBoxRetriever =>
        if (!fullNoteHasValue(x))
          Set(CtValidation(None, "error.balanceSheet.creditorsAfterOneYear.mustNotBeEmpty"))
        else
          Set.empty

      case _ => Set.empty
    }
  }

}
