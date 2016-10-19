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

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC115(value: Option[Int]) extends CtBoxIdentifier(name = "Additions")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs102AccountsBoxRetriever]
  with Validators {

  def getNoteValues(boxRetriever: Frs102AccountsBoxRetriever) = {
    import boxRetriever._

    Set(
      ac114().value,
      ac115().value,
      ac116().value,
      ac209().value,
      ac210().value,
      ac118().value,
      ac119().value,
      ac120().value,
      ac211().value
    )
  }

  def validateNoteEntered(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    val noteValues = getNoteValues(boxRetriever)

    (noteValues.exists(_.nonEmpty), ac5123().value.getOrElse("").trim().nonEmpty) match {
      case (false, false) => Set(CtValidation(None, "error.balanceSheet.intangibleAssets.atLeastOneEntered"))
      case _ => Set.empty
    }
  }

  def validateNoteCannotExists(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    val noteValues = getNoteValues(boxRetriever)
    val noteIsNotEmpty = noteValues.exists(_.nonEmpty) || ac5123().value.getOrElse("").trim().nonEmpty

    (ac42.value.isEmpty, noteIsNotEmpty) match {
      case (true, true) => Set(CtValidation(None, "error.balanceSheet.intangibleAssetsNote.cannot.exist"))
      case _ => Set.empty
    }
  }

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {

    collectErrors(
      failIf(boxRetriever.ac42.nonEmpty)(validateNoteEntered(boxRetriever)),
      failIf(boxRetriever.ac42.isEmpty)(validateNoteCannotExists(boxRetriever)),
      validateMoney(value, min = 0)
    )
  }
}
