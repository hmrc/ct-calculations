/*
 * Copyright 2019 HM Revenue & Customs
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

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger, CtTypeConverters}
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

case class B295(value: Int) extends CtBoxIdentifier(name = "Total of deductions and reliefs") with CtInteger

object B295 extends Calculated[B295, CT600BoxRetriever] with CtTypeConverters {
  override def calculate(boxRetriever: CT600BoxRetriever): B295 = {
    B295(boxRetriever.b275() + boxRetriever.b285())
  }
}
