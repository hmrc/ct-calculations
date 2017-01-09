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

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalBoolean}
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

case class E25(value: Option[Boolean]) extends CtBoxIdentifier("Some of the income and gains may not be exempt or have not been applied for charitable or qualifying purposes only, and I have completed form CT600") with CtOptionalBoolean

object E25 extends Calculated[E25, CT600EBoxRetriever] {
  override def calculate(boxRetriever: CT600EBoxRetriever): E25 = {

    E25(boxRetriever.e20().inverse)
  }
}
