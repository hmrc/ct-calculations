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

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.ct600.v2.retriever.CT600BoxRetriever

case class B18(value: Int) extends CtBoxIdentifier(name = " Net chargeable gains") with CtInteger

object B18 extends Calculated[B18, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B18 = {
//    B18(fieldValueRetriever.b16() - fieldValueRetriever.b17())
    ???
  }
}
