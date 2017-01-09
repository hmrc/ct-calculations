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

package uk.gov.hmrc.ct.ct600j.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600j.v3.retriever.CT600JBoxRetriever

abstract class SchemeReferenceNumberBox extends CtBoxIdentifier("Scheme reference number") with CtOptionalString with Input with ValidatableBox[CT600JBoxRetriever] {

  def validateSchemeReferenceNumber(previousSchemeNumberBox: CtOptionalString, previousSchemeDateBox: CtOptionalDate, schemeDateBox: CtOptionalDate) = (previousSchemeNumberBox.value, previousSchemeDateBox.value, schemeDateBox.value) match {
    case (None, None, _) => validateStringAsBlank(id, this)
    case (_, _, Some(_)) => validateAsMandatory(this) ++ validateOptionalStringByRegex(id, this, taxAvoidanceSchemeNumberRegex)
    case _ => validateOptionalStringByRegex(id, this, taxAvoidanceSchemeNumberRegex)
  }
}
