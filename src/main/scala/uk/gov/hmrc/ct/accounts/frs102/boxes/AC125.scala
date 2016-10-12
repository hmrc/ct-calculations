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

import uk.gov.hmrc.ct.accounts.frs102.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC125(value: Option[Int]) extends CtBoxIdentifier(name = "The cost of all tangible assets acquired during this period")
  with CtOptionalInteger
  with Input
  with ValidatableBox[AbridgedAccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: AbridgedAccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      failIf(boxRetriever.ac44().value.nonEmpty)(
        collectErrors(
          validateMoney(value, min = 0),
          validateOneFieldMandatory(boxRetriever)
        )
      ),
      failIf(boxRetriever.ac44().value.isEmpty)(validateNoteCannotExist(boxRetriever))
    )
  }

  private def validateNoteCannotExist(boxRetriever: AbridgedAccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    val values = Seq(
      ac124().value,
      ac125().value,
      ac126().value,
      ac212().value,
      ac213().value,
      ac128().value,
      ac219().value,
      ac130().value,
      ac214().value
    )

    if (values.exists(_.nonEmpty) || ac5133().value.getOrElse("").trim().nonEmpty)
      Set(CtValidation(None, "error.balanceSheet.tangibleAssetsNote.cannot.exist"))
    else
      Set.empty
  }

  private def validateOneFieldMandatory(boxRetriever: AbridgedAccountsBoxRetriever)() = {
    val anyBoxPopulated = (
        boxRetriever.ac124().value orElse
        boxRetriever.ac125().value orElse
        boxRetriever.ac126().value orElse
        boxRetriever.ac212().value orElse
        boxRetriever.ac213().value orElse
        boxRetriever.ac128().value orElse
        boxRetriever.ac219().value orElse
        boxRetriever.ac130().value orElse
        boxRetriever.ac214().value orElse
        boxRetriever.ac5133().value
      ).nonEmpty

    failIf(!anyBoxPopulated)(
      Set(CtValidation(None, "error.tangible.assets.note.one.box.required"))
    )
  }
}
