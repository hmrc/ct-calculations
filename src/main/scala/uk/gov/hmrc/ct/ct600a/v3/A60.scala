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

package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600.v3.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever

case class A60(value: Option[Int]) extends CtBoxIdentifier(name = "A60 - Date that part/whole loan released/written off more than 9 months after period end date and date completing return is later than resolution date")
with CtOptionalInteger

object A60 extends Calculated[A60, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

 override def calculate(fieldValueRetriever: CT600ABoxRetriever): A60 = {
  import fieldValueRetriever._
  calculateA60(cp2(), loansToParticipators(), lpq07())
 }
}
