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

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalBoolean}
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

case class E1012(value: Option[Boolean]) extends CtBoxIdentifier("Some not only charitable") with CtOptionalBoolean

object E1012 extends Calculated[E1012, CT600EBoxRetriever] {
  override def calculate(boxRetriever: CT600EBoxRetriever): E1012 = {
    E1012(boxRetriever.e1011().inverse)
  }
}
