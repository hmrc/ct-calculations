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

package uk.gov.hmrc.ct.accounts.frs102.validation

import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait DirectorsReportEnabledCalculator {
  def directorsReportEnabled(boxRetriever: Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever): Boolean = {
    val isCoHoFiling = boxRetriever.companiesHouseFiling().value
    val isHmrcFiling = boxRetriever.hmrcFiling().value
    val isMicroEntityFiling = boxRetriever.microEntityFiling().value
    val answeredYesToCoHoDirectorsReportQuestion = boxRetriever.ac8021().orFalse
    val answeredYesToHmrcDirectorsReportQuestion = boxRetriever.ac8023().orFalse

    (isCoHoFiling, isHmrcFiling) match {
      case (true, false) => answeredYesToCoHoDirectorsReportQuestion
      case _ => !isMicroEntityFiling || answeredYesToHmrcDirectorsReportQuestion
    }
  }
}
