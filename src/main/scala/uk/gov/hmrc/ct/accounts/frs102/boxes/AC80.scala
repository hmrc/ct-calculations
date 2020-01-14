/*
 * Copyright 2020 HM Revenue & Customs
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

import uk.gov.hmrc.ct.accounts.frs102.calculations.TotalShareholdersFundsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.validation.AssetsEqualToSharesValidator
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class AC80(value: Option[Int]) extends CtBoxIdentifier(name = "Total Shareholders Funds (current PoA)")
  with CtOptionalInteger with AssetsEqualToSharesValidator with ValidatableBox[Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever] {
 
  override def validate(boxRetriever: Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    validateAssetsEqualToShares("AC80", boxRetriever.ac68(), boxRetriever.companyType().isLimitedByGuarantee)
  }
}

object AC80 extends Calculated[AC80, Frs102AccountsBoxRetriever] with TotalShareholdersFundsCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC80 = {
    import boxRetriever._
    calculateCurrentTotalShareholdersFunds(ac70(), ac76(), ac74())
  }
}
