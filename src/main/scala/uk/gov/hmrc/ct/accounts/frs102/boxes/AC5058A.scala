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

case class AC5058A(value: Option[String]) extends CtBoxIdentifier(name = "Balance sheet - Creditors within 1 year note.") with CtOptionalString with Input with SelfValidatableBox[Frs102AccountsBoxRetriever, Option[String]] {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors (
      failIf(!boxRetriever.ac58().hasValue && !boxRetriever.ac59().hasValue)(validateCannotExist(boxRetriever)),
      failIf(boxRetriever.ac58().hasValue || boxRetriever.ac59().hasValue)(validateNoteIsMandatory(boxRetriever)),
      validateStringMaxLength(value.getOrElse(""), StandardCohoTextFieldLimit),
      validateCoHoStringReturnIllegalChars()
    )
  }

  private def validateCannotExist(boxRetriever: Frs102AccountsBoxRetriever)(): Set[CtValidation] = {
    boxRetriever match {
      case x: AbridgedAccountsBoxRetriever => failIf(hasValue)(Set(CtValidation(None, "error.balanceSheet.creditorsWithinOneYear.cannotExist")))
      case x: FullAccountsBoxRetriever => failIf(fullNoteHasValue(x))(Set(CtValidation(None, "error.balanceSheet.creditorsWithinOneYear.cannotExist")))
      case _ => Set.empty
    }
  }

  private def validateNoteIsMandatory(boxRetriever: Frs102AccountsBoxRetriever)(): Set[CtValidation] = {
    boxRetriever match {
      case x: FullAccountsBoxRetriever =>
        failIf(!fullNoteHasValue(x))(Set(CtValidation(None, "error.creditors.within.one.year.note.one.box.required")))
      case _ => Set.empty
    }
  }

  private def fullNoteHasValue(boxRetriever: FullAccountsBoxRetriever): Boolean = {
    import boxRetriever._

    ac142().hasValue || ac143().hasValue ||
      ac144().hasValue || ac145().hasValue ||
      ac146().hasValue || ac147().hasValue ||
      ac148().hasValue || ac149().hasValue ||
      ac150().hasValue || ac151().hasValue ||
      ac152().hasValue || ac153().hasValue ||
      hasValue
  }
}
