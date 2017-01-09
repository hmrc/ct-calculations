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

  private def validateFullNoteCannotExist(boxRetriever: FullAccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    val values = Seq(
      ac124().value,
      ac124A().value,
      ac124B().value,
      ac124C().value,
      ac124D().value,
      ac124E().value,
      ac125().value,
      ac125A().value,
      ac125B().value,
      ac125C().value,
      ac125D().value,
      ac125E().value,
      ac126().value,
      ac126A().value,
      ac126B().value,
      ac126C().value,
      ac126D().value,
      ac126E().value,
      ac127().value,
      ac127A().value,
      ac127B().value,
      ac127C().value,
      ac127D().value,
      ac127E().value,
      ac128().value,
      ac128A().value,
      ac128B().value,
      ac128C().value,
      ac128D().value,
      ac128E().value,
      ac129().value,
      ac129A().value,
      ac129B().value,
      ac129C().value,
      ac129D().value,
      ac129E().value,
      ac130().value,
      ac130A().value,
      ac130B().value,
      ac130C().value,
      ac130D().value,
      ac130E().value,
      ac131().value,
      ac131A().value,
      ac131B().value,
      ac131C().value,
      ac131D().value,
      ac131E().value,
      ac132().value,
      ac132A().value,
      ac132B().value,
      ac132C().value,
      ac132D().value,
      ac132E().value,
      ac133().value,
      ac133A().value,
      ac133B().value,
      ac133C().value,
      ac133D().value,
      ac133E().value,
      ac212().value,
      ac212A().value,
      ac212B().value,
      ac212C().value,
      ac212D().value,
      ac212E().value,
      ac213().value,
      ac213A().value,
      ac213B().value,
      ac213C().value,
      ac213D().value,
      ac213E().value,
      ac214().value,
      ac214A().value,
      ac214B().value,
      ac214C().value,
      ac214D().value,
      ac214E().value
    )

    if (values.exists(_.nonEmpty) || ac5133().value.getOrElse("").trim().nonEmpty)
      Set(CtValidation(None, "error.balanceSheet.tangibleAssetsNote.cannot.exist"))
    else
      Set.empty
  }

  private def validateAbridgedOneFieldMandatory(boxRetriever: Frs102AccountsBoxRetriever)(): Set[CtValidation] = {
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

  private def validateFullOneFieldMandatory(boxRetriever: FullAccountsBoxRetriever)(): Set[CtValidation] = {
    import boxRetriever._

    val anyBoxPopulated = (
      ac124().value orElse
      ac124A().value orElse
      ac124B().value orElse
      ac124C().value orElse
      ac124D().value orElse
      ac124E().value orElse
      ac125().value orElse
      ac125A().value orElse
      ac125B().value orElse
      ac125C().value orElse
      ac125D().value orElse
      ac125E().value orElse
      ac126().value orElse
      ac126A().value orElse
      ac126B().value orElse
      ac126C().value orElse
      ac126D().value orElse
      ac126E().value orElse
      ac127().value orElse
      ac127A().value orElse
      ac127B().value orElse
      ac127C().value orElse
      ac127D().value orElse
      ac127E().value orElse
      ac128().value orElse
      ac128A().value orElse
      ac128B().value orElse
      ac128C().value orElse
      ac128D().value orElse
      ac128E().value orElse
      ac129().value orElse
      ac129A().value orElse
      ac129B().value orElse
      ac129C().value orElse
      ac129D().value orElse
      ac129E().value orElse
      ac130().value orElse
      ac130A().value orElse
      ac130B().value orElse
      ac130C().value orElse
      ac130D().value orElse
      ac130E().value orElse
      ac131().value orElse
      ac131A().value orElse
      ac131B().value orElse
      ac131C().value orElse
      ac131D().value orElse
      ac131E().value orElse
      ac132().value orElse
      ac132A().value orElse
      ac132B().value orElse
      ac132C().value orElse
      ac132D().value orElse
      ac132E().value orElse
      ac133().value orElse
      ac133A().value orElse
      ac133B().value orElse
      ac133C().value orElse
      ac133D().value orElse
      ac133E().value orElse
      ac212().value orElse
      ac212A().value orElse
      ac212B().value orElse
      ac212C().value orElse
      ac212D().value orElse
      ac212E().value orElse
      ac213().value orElse
      ac213A().value orElse
      ac213B().value orElse
      ac213C().value orElse
      ac213D().value orElse
      ac213E().value orElse
      ac214().value orElse
      ac214A().value orElse
      ac214B().value orElse
      ac214C().value orElse
      ac214D().value orElse
      ac214E().value orElse
      ac5133().value
    ).nonEmpty

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
