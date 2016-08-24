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

import uk.gov.hmrc.ct.accounts.frs10x.abridged.calculations.RevaluationReserveCalculator
import uk.gov.hmrc.ct.accounts.frs10x.abridged.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC5076B(value: Option[Int]) extends CtBoxIdentifier(name = "Balance at [POA END DATE]")
                                       with CtOptionalInteger
                                       with ValidatableBox[AbridgedAccountsBoxRetriever]
                                       with Validators {

  override def validate(boxRetriever: AbridgedAccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      failIf(boxRetriever.ac76().value.nonEmpty) {
        validateOptionalIntegerAsEqualTo(this, boxRetriever.ac76())
      }
    )
  }

}

object AC5076B extends Calculated[AC5076B, AbridgedAccountsBoxRetriever]
               with RevaluationReserveCalculator {

  override def calculate(boxRetriever: AbridgedAccountsBoxRetriever): AC5076B = {
    calculateAC5076B(boxRetriever.ac76(), boxRetriever.ac77(), boxRetriever.ac5076A())
  }

}
