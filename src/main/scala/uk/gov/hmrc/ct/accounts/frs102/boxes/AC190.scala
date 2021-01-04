/*
 * Copyright 2021 HM Revenue & Customs
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

import uk.gov.hmrc.ct.accounts.frs102.retriever.{Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDormancyBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever._

case class AC190(value: Option[Int]) extends CtBoxIdentifier(name = "Balance at [POA END DATE]")
                                       with CtOptionalInteger
                                       with CtTypeConverters
                                       with ValidatableBox[Frs102AccountsBoxRetriever  with Frs10xDormancyBoxRetriever]
                                       with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever with Frs10xDormancyBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    failIf (anyHaveValue(ac76(), ac77()))(
      collectErrors(
        validateTotalEqualToCurrentAmount(boxRetriever)
      )
    )
  }

  def validateTotalEqualToCurrentAmount(boxRetriever: Frs102AccountsBoxRetriever with Frs10xDormancyBoxRetriever)() = {
    val ac76 = boxRetriever.ac76()

    val dormant = boxRetriever.acq8999().orFalse

    val notEqualToAC76 = ac76.value.getOrElse(0) != this.value.getOrElse(0)

    failIf(notEqualToAC76 && (!dormant || boxRetriever.ac187())) {
      Set(CtValidation(None, "error.AC190.mustEqual.AC76"))
    }
  }

}

object AC190 extends Calculated[AC190, Frs102AccountsBoxRetriever]
               with CtTypeConverters {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC190 = {
    // From now on AC189 should have a value only if AC187 is true. However, it needs to be checked
    // in case there are incomplete filings that have AC189 only at the point of release.
    if (boxRetriever.ac187() || boxRetriever.ac189().hasValue) {
      AC190(Some(boxRetriever.ac77 + boxRetriever.ac189()))
    }
    else {
      AC190(None)
    }
  }

}
