/*
 * Copyright 2016 HM Revenue & Customs
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

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

case class E1030(value: Option[String]) extends CtBoxIdentifier("Claimer's name") with CtOptionalString with Input with ValidatableBox[CT600EBoxRetriever]{
  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] =
    validateStringAsMandatory("E1030", this) ++ validateOptionalStringByLength("E1030", this, 2, 56) ++ validateOptionalStringByRegex("E1030", this, validNonForeignLessRestrictiveCharacters)
}
