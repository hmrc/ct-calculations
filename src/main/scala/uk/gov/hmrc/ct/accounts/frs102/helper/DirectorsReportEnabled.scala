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

package uk.gov.hmrc.ct.accounts.frs102.helper

import uk.gov.hmrc.ct.accounts.frs102.validation.DirectorsReportEnabledCalculator
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoolean, CtBoxIdentifier}

case class DirectorsReportEnabled(value: Boolean) extends CtBoxIdentifier(name = "Do you want to file a directors' report to Companies House?") with CtBoolean

object DirectorsReportEnabled extends Calculated[DirectorsReportEnabled, Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever] with DirectorsReportEnabledCalculator {

  override def calculate(boxRetriever: Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever): DirectorsReportEnabled = {
    DirectorsReportEnabled(directorsReportEnabled(boxRetriever))
  }
}
