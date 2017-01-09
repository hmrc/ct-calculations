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

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalBoolean}
import uk.gov.hmrc.ct.ct600a.v2.A6
import uk.gov.hmrc.ct.ct600a.v2.A6._
import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever

case class B80(value: Option[Boolean]) extends CtBoxIdentifier(name = "B80 - Completed box A11 in the Supplementary Pages CT600A") with CtOptionalBoolean

object B80 extends Calculated[B80, CT600ABoxRetriever] {

  override def calculate(fieldValueRetriever: CT600ABoxRetriever): B80 = {
    calculateB80(fieldValueRetriever.a11())
  }
}
