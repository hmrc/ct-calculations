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

package uk.gov.hmrc.ct.accounts.frs10x

import uk.gov.hmrc.ct.accounts.frs10x.abridged.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class ACQ8161(value: Option[Boolean]) extends CtBoxIdentifier(name = "Do you want to file P&L to Companies House?")
                                           with CtOptionalBoolean
                                           with Input
                                           with ValidatableBox[AbridgedAccountsBoxRetriever with FilingAttributesBoxValueRetriever] {


  override def validate(boxRetriever: AbridgedAccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    failIf(boxRetriever.companiesHouseFiling().value)(
      collectErrors(
        validateBooleanAsMandatory("ACQ8161", this),
        validateCannotExist(boxRetriever)
      )
    )
  }

  def validateCannotExist(boxRetriever: AbridgedAccountsBoxRetriever)(): Set[CtValidation] = {
    import boxRetriever._

    if (value.contains(false)) {
      val noteNonEmpty =
        ac16().value.nonEmpty ||
        ac17().value.nonEmpty ||
        ac18().value.nonEmpty ||
        ac19().value.nonEmpty ||
        ac20().value.nonEmpty ||
        ac21().value.nonEmpty ||
        ac26().value.nonEmpty ||
        ac27().value.nonEmpty ||
        ac28().value.nonEmpty ||
        ac29().value.nonEmpty ||
        ac30().value.nonEmpty ||
        ac31().value.nonEmpty ||
        ac34().value.nonEmpty ||
        ac35().value.nonEmpty ||
        ac36().value.nonEmpty ||
        ac37().value.nonEmpty ||
        ac5032().value.nonEmpty

      if (noteNonEmpty)
        Set(CtValidation(None, "error.profitAndLoss.cannot.exist"))
      else
        Set.empty
    } else Set.empty
  }

}
