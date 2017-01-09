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

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.frs102.validation.DirectorsReportExistenceValidation
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class AC8021(value: Option[Boolean]) extends CtBoxIdentifier(name = "Do you want to file a directors' report to Companies House?")
                                          with CtOptionalBoolean
                                          with Input
                                          with SelfValidatableBox[Frs10xDirectorsBoxRetriever
                                          with FilingAttributesBoxValueRetriever, Option[Boolean]]
                                          with DirectorsReportExistenceValidation {

  override def validate(boxRetriever: Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    val coHoFiling = boxRetriever.companiesHouseFiling().value
    val hmrcFiling = boxRetriever.hmrcFiling().value
    val microEntityFiling = boxRetriever.microEntityFiling().value
    val fileDRToHmrc = boxRetriever.ac8023().orFalse

    // This field is required if filing non micro-entity Joint or to CoHo only
    // or when filing Joint micro-entity AND answered "true" to "AC8023".
    // In the last case, answering "false" disables "Directors report" section - including this question.
    failIf(coHoFiling && (!(hmrcFiling && microEntityFiling) || (hmrcFiling && microEntityFiling && fileDRToHmrc)))(
      collectErrors(
        validateAsMandatory(),
        // Validate cannot exist only if filing for CoHo only
        failIf(!hmrcFiling)(validateDirectorsReportCannotExist("AC8021", value, boxRetriever))
      )
    )
  }

}
