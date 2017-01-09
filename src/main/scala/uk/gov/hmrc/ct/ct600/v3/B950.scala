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

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

case class B950(value: Option[String]) extends CtBoxIdentifier("Company name") with CtOptionalString with Input

object B950 extends Calculated[B950, BoxRetriever] {
  override def calculate(boxRetriever: BoxRetriever): B950 = {
    boxRetriever match {
      case br: CT600BoxRetriever => B950(Some(br.b1().value))
      case br: CT600EBoxRetriever => B950(Some(br.e1().value))
      case _ => throw new IllegalStateException(s"Could not find the company name from the supplied retriever: $boxRetriever")
    }
  }
}
