/*
 * Copyright 2024 HM Revenue & Customs
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

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v2.retriever.ReturnStatementsBoxRetriever
import uk.gov.hmrc.ct.ct600.v2.validation.RSQ7MutuallyExclusiveWithRSQ8

case class RSQ8(value: Option[Boolean]) extends CtBoxIdentifier
  with CtOptionalBoolean with Input with ValidatableBox[ReturnStatementsBoxRetriever] with RSQ7MutuallyExclusiveWithRSQ8 {

  override def validate(boxRetriever: ReturnStatementsBoxRetriever): Set[CtValidation] = {
    validateAsMandatory(this) ++ validateMutualExclusivity(boxRetriever)
  }
}
