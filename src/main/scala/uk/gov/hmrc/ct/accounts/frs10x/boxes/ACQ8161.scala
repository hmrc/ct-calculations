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

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box.retriever.BoxRetriever._


case class ACQ8161(value: Option[Boolean]) extends CtBoxIdentifier(name = "Do you want to file P&L to Companies House?")
                                           with CtOptionalBoolean
                                           with Input
                                           with ValidatableBox[AccountsBoxRetriever with FilingAttributesBoxValueRetriever] {

  override def validate(boxRetriever: AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    collectErrors(
      failIf(boxRetriever.companiesHouseFiling().value)(
        validateBooleanAsMandatory("ACQ8161", this)
      ),
      passIf(boxRetriever.hmrcFiling().value)(
        boxRetriever match {
          case boxRetriever: Frs102AccountsBoxRetriever => validateCannotExist(boxRetriever)
          case boxRetriever: Frs105AccountsBoxRetriever => validateCannotExist(boxRetriever)
          case unknown => throw new IllegalStateException("unexpected retriever type: " + unknown)
        }
      )
    )
  }

  def validateCannotExist(boxRetriever: Frs102AccountsBoxRetriever)(): Set[CtValidation] = {
    import boxRetriever._

    if (value.contains(false)) {
      val noteNonEmpty = anyHaveValue(
        ac16,
        ac17,
        ac18,
        ac19,
        ac20,
        ac21,
        ac26,
        ac27,
        ac28,
        ac29,
        ac30,
        ac31,
        ac34,
        ac35,
        ac36,
        ac37,
        ac5032
      )

      if (noteNonEmpty)
        Set(CtValidation(None, "error.profitAndLoss.cannot.exist"))
      else
        Set.empty
    } else Set.empty
  }

  def validateCannotExist(boxRetriever: Frs105AccountsBoxRetriever)(): Set[CtValidation] = {
    import boxRetriever._

    if (value.contains(false)) {
      val noteNonEmpty = anyHaveValue(
        ac405(),
          ac406(),
          ac410(),
          ac411(),
          ac415(),
          ac416(),
          ac420(),
          ac421(),
          ac425(),
          ac426(),
          ac34(),
          ac35()
      )
      if (noteNonEmpty)
        Set(CtValidation(None, "error.profitAndLoss.cannot.exist"))
      else
        Set.empty
    } else Set.empty
  }
}
