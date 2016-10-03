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

package uk.gov.hmrc.ct.accounts.frs10x.abridged

import uk.gov.hmrc.ct.accounts.frs10x.abridged.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.box._


case class AC189(value: Option[Int]) extends CtBoxIdentifier(name = "Surplus or deficit after revaluation") with CtOptionalInteger
                                                                                                              with Input
                                                                                                              with ValidatableBox[AbridgedAccountsBoxRetriever]
                                                                                                              with Validators {

  override def validate(boxRetriever: AbridgedAccountsBoxRetriever): Set[CtValidation] = {
    collectErrors (
      failIf(boxRetriever.ac76().value.isDefined)(validateIntegerAsMandatory("AC189", this)),
      failIf(boxRetriever.ac76().value.isEmpty)(validateNoteCannotExist(boxRetriever)),
      validateMoney(value)
    )
  }

  def validateNoteCannotExist(boxRetriever: AbridgedAccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    val isNoteNonEmpty = ac189().value.nonEmpty || ac5076C().value.getOrElse("").trim().nonEmpty

    if (isNoteNonEmpty)
      Set(CtValidation(None, "error.balanceSheet.revaluationReserveNote.cannot.exist"))
    else
      Set.empty
  }

}

