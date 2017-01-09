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

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever._

case class AC189(value: Option[Int]) extends CtBoxIdentifier(name = "Surplus or deficit after revaluation") with CtOptionalInteger
                                                                                                              with Input
                                                                                                              with ValidatableBox[Frs102AccountsBoxRetriever]
                                                                                                              with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._
    val isMandatory = anyHaveValue(ac76(), ac77())

    collectErrors (
      failIf(isMandatory)(validateIntegerAsMandatory("AC189", this)),
      failIf(!isMandatory)(validateNoteCannotExist(boxRetriever)),
      validateMoney(value)
    )
  }

  def validateNoteCannotExist(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    val isNoteNonEmpty = ac189().hasValue || ac5076C().value.getOrElse("").trim().nonEmpty

    if (isNoteNonEmpty)
      Set(CtValidation(None, "error.balanceSheet.revaluationReserveNote.cannot.exist"))
    else
      Set.empty
  }

}
