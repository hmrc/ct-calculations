/*
 * Copyright 2015 HM Revenue & Customs
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

package uk.gov.hmrc.ct.ct600j.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

case class J35(value: Option[String]) extends CtBoxIdentifier("Scheme reference number") with CtOptionalString with Input with ValidatableBox[CT600BoxRetriever] {

  val boxNumber = "J35"

  override def validate(boxRetriever: CT600BoxRetriever): Set[CtValidation] = {
    if(boxRetriever.retrieveJ30().value.isEmpty) {
      validateStringAsBlank(boxNumber, this)
    } else {
      validateOptionalStringByRegex(boxNumber, this, taxAvoidanceSchemeNumberRegex)
    }
  }

}
