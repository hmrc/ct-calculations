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

package uk.gov.hmrc.ct.ct600a.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600.v2.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever

case class A8(value: Option[Int]) extends CtBoxIdentifier(name = "A8 - Information about loans made during the return period which have been repaid more than nine months after the end of the period and relief is due now")
 with CtOptionalInteger

object A8 extends Calculated[A8, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(fieldValueRetriever: CT600ABoxRetriever): A8 = {
    import fieldValueRetriever._
    calculateA8(cp2(), lp02(), lpq07())
  }
}
