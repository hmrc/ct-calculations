/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.ct.computations.covidSupport

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP126(value: Int) extends CtBoxIdentifier(name = "Coronavirus support schemes overpayment now due") with CtInteger

object CP126 extends Calculated[CP126, ComputationsBoxRetriever] {
  override def calculate(boxRetriever: ComputationsBoxRetriever): CP126 = {
    val cp122 = boxRetriever.cp122()
    val cp123 = boxRetriever.cp123()
    val cp124 = boxRetriever.cp124()
    val cp125 = boxRetriever.cp125()

    CP126(cp122.value.getOrElse(0) - cp123.value.getOrElse(0) - cp124.value.getOrElse(0) + cp125.value.getOrElse(0))
  }
}