/*
 * Copyright 2018 HM Revenue & Customs
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

import uk.gov.hmrc.ct.accounts.frs102.calculations.BalanceSheetTangibleAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever._

case class AC125(value: Option[Int]) extends CtBoxIdentifier(name = "The cost of all tangible assets acquired during this period")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs102AccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    val isMandatory = anyHaveValue(ac44(), ac45())

    boxRetriever match {
      case x: AbridgedAccountsBoxRetriever =>
        collectErrors(
          failIf(isMandatory)(
            collectErrors(
              validateMoney(value, min = 0),
              validateAbridgedOneFieldMandatory(boxRetriever)
            )
          ),
          failIf(!isMandatory)(validateAbridgedNoteCannotExist(boxRetriever))
        )
      case x: FullAccountsBoxRetriever =>
        collectErrors(
          failIf(isMandatory)(
            collectErrors(
              validateMoney(value, min = 0),
              validateFullOneFieldMandatory(x)
            )
          ),
          failIf(!isMandatory)(validateFullNoteCannotExist(x))
        )
    }
  }

  private def validateAbridgedNoteCannotExist(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    val anyBoxPopulated = Stream(
      ac124(),
      ac125(),
      ac126(),
      ac212(),
      ac213(),
      ac128(),
      ac219(),
      ac130(),
      ac214()
    ).exists(_.value.nonEmpty)

    if (anyBoxPopulated || ac5133().value.getOrElse("").trim().nonEmpty)
      Set(CtValidation(None, "error.balanceSheet.tangibleAssetsNote.cannot.exist"))
    else
      Set.empty
  }

  private def validateFullNoteCannotExist(boxRetriever: FullAccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    val anyBoxPopulated = Seq(
      ac124(),
      ac124A(),
      ac124B(),
      ac124C(),
      ac124D(),
      ac124E(),
      ac125(),
      ac125A(),
      ac125B(),
      ac125C(),
      ac125D(),
      ac125E(),
      ac126(),
      ac126A(),
      ac126B(),
      ac126C(),
      ac126D(),
      ac126E(),
      ac127(),
      ac127A(),
      ac127B(),
      ac127C(),
      ac127D(),
      ac127E(),
      ac128(),
      ac128A(),
      ac128B(),
      ac128C(),
      ac128D(),
      ac128E(),
      ac129(),
      ac129A(),
      ac129B(),
      ac129C(),
      ac129D(),
      ac129E(),
      ac130(),
      ac130A(),
      ac130B(),
      ac130C(),
      ac130D(),
      ac130E(),
      ac131(),
      ac131A(),
      ac131B(),
      ac131C(),
      ac131D(),
      ac131E(),
      ac132(),
      ac132A(),
      ac132B(),
      ac132C(),
      ac132D(),
      ac132E(),
      ac133(),
      ac133A(),
      ac133B(),
      ac133C(),
      ac133D(),
      ac133E(),
      ac212(),
      ac212A(),
      ac212B(),
      ac212C(),
      ac212D(),
      ac212E(),
      ac213(),
      ac213A(),
      ac213B(),
      ac213C(),
      ac213D(),
      ac213E(),
      ac214(),
      ac214A(),
      ac214B(),
      ac214C(),
      ac214D(),
      ac214E()
    ).exists(_.value.nonEmpty)

    if (anyBoxPopulated || ac5133().value.getOrElse("").trim().nonEmpty)
      Set(CtValidation(None, "error.balanceSheet.tangibleAssetsNote.cannot.exist"))
    else
      Set.empty
  }

  private def validateAbridgedOneFieldMandatory(boxRetriever: Frs102AccountsBoxRetriever)(): Set[CtValidation] = {
    val anyBoxPopulated: Boolean = Stream (
        boxRetriever.ac124(),
        boxRetriever.ac125(),
        boxRetriever.ac126(),
        boxRetriever.ac212(),
        boxRetriever.ac213(),
        boxRetriever.ac128(),
        boxRetriever.ac219(),
        boxRetriever.ac130(),
        boxRetriever.ac214(),
        boxRetriever.ac5133()
      ).exists(_.value.nonEmpty)

    failIf(!anyBoxPopulated)(
      Set(CtValidation(None, "error.tangible.assets.note.one.box.required"))
    )
  }

  private def validateFullOneFieldMandatory(boxRetriever: FullAccountsBoxRetriever)(): Set[CtValidation] = {
    import boxRetriever._

    val anyBoxPopulated = Stream(
      ac124(),
      ac124A(),
      ac124B(),
      ac124C(),
      ac124D(),
      ac124E(),
      ac125(),
      ac125A(),
      ac125B(),
      ac125C(),
      ac125D(),
      ac125E(),
      ac126(),
      ac126A(),
      ac126B(),
      ac126C(),
      ac126D(),
      ac126E(),
      ac127(),
      ac127A(),
      ac127B(),
      ac127C(),
      ac127D(),
      ac127E(),
      ac128(),
      ac128A(),
      ac128B(),
      ac128C(),
      ac128D(),
      ac128E(),
      ac129(),
      ac129A(),
      ac129B(),
      ac129C(),
      ac129D(),
      ac129E(),
      ac130(),
      ac130A(),
      ac130B(),
      ac130C(),
      ac130D(),
      ac130E(),
      ac131(),
      ac131A(),
      ac131B(),
      ac131C(),
      ac131D(),
      ac131E(),
      ac132(),
      ac132A(),
      ac132B(),
      ac132C(),
      ac132D(),
      ac132E(),
      ac133(),
      ac133A(),
      ac133B(),
      ac133C(),
      ac133D(),
      ac133E(),
      ac212(),
      ac212A(),
      ac212B(),
      ac212C(),
      ac212D(),
      ac212E(),
      ac213(),
      ac213A(),
      ac213B(),
      ac213C(),
      ac213D(),
      ac213E(),
      ac214(),
      ac214A(),
      ac214B(),
      ac214C(),
      ac214D(),
      ac214E(),
      ac5133()
    ).exists(_.value.nonEmpty)

    failIf(!anyBoxPopulated)(
      Set(CtValidation(None, "error.tangible.assets.note.one.box.required"))
    )
  }
}

object AC125 extends Calculated[AC125, FullAccountsBoxRetriever] with BalanceSheetTangibleAssetsCalculator {

  override def calculate(boxRetriever: FullAccountsBoxRetriever): AC125 = {
    import boxRetriever._
    calculateAC125(
      ac125A(),
      ac125B(),
      ac125C(),
      ac125D(),
      ac125E()
    )
  }
}
