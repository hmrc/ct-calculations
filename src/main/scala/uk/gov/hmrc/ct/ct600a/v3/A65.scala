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

case class A65(value: Option[Int]) extends CtBoxIdentifier(name = "A65 - Total amount of loans made during the return period which have been repaid, released, or written off more than nine months after the end of the period and relief is due now")
with CtOptionalInteger

object A65 extends Calculated[A65, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

 override def calculate(fieldValueRetriever: CT600ABoxRetriever): A65 = {
  calculateA65(fieldValueRetriever.a55(), fieldValueRetriever.a60())
 }
}
