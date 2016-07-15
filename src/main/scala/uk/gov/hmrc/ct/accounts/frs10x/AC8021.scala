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

import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class AC8021(value: Option[Boolean]) extends CtBoxIdentifier(name = "Do you want to file a directors' report to Companies House?") with CtOptionalBoolean with Input with ValidatableBox[Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever] {
  override def validate(boxRetriever: Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    val coHoFiling = boxRetriever.retrieveCompaniesHouseFiling().value
    val hmrcFiling = boxRetriever.retrieveHMRCFiling().value
    val microEntityFiling = boxRetriever.retrieveMicroEntityFiling().value
    val fileDRToHmrc = boxRetriever.retrieveAC8023().orFalse

    // This field is required if filing non micro-entity Joint or to CoHo only
    // or when filing Joint micro-entity AND answered "true" to "AC8023".
    // In the last case, answering "false" disables "Directors report" section - including this question.
    if (coHoFiling && (!(hmrcFiling && microEntityFiling) || (hmrcFiling && microEntityFiling && fileDRToHmrc)))
      validateBooleanAsMandatory("AC8021", this)
    else
      Set.empty
  }
}