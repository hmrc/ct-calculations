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

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class ACQ8161(value: Option[Boolean]) extends CtBoxIdentifier(name = "Do you want to file P&L to Companies House?")
                                           with CtOptionalBoolean
                                           with Input
                                           with ValidatableBox[Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever] {

  override def validate(boxRetriever: Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    collectErrors(
      failIf(boxRetriever.companiesHouseFiling().value)(
        validateBooleanAsMandatory("ACQ8161", this)
      ),
      passIf(boxRetriever.hmrcFiling().value)(
        validateCannotExist(boxRetriever)
      )
    )
  }

  def validateCannotExist(boxRetriever: Frs105AccountsBoxRetriever)(): Set[CtValidation] = {
    import boxRetriever._

    if (value.contains(false)) {
      val noteNonEmpty =
        ac405().hasValue ||
        ac406().hasValue ||
        ac410().hasValue ||
        ac411().hasValue ||
        ac415().hasValue ||
        ac416().hasValue ||
        ac420().hasValue ||
        ac421().hasValue ||
        ac425().hasValue ||
        ac426().hasValue ||
        ac34().hasValue ||
        ac35().hasValue

      if (noteNonEmpty)
        Set(CtValidation(None, "error.profitAndLoss.cannot.exist"))
      else
        Set.empty
    } else Set.empty
  }

}
