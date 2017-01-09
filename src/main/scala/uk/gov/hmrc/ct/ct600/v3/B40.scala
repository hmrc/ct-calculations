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
import uk.gov.hmrc.ct.ct600.v3.retriever.AboutThisReturnBoxRetriever


case class B40(value: Option[Boolean]) extends CtBoxIdentifier("A repayment is due for this return period")
  with CtOptionalBoolean with Input with ValidatableBox[AboutThisReturnBoxRetriever] {

  override def validate(boxRetriever: AboutThisReturnBoxRetriever): Set[CtValidation] = validateBooleanAsMandatory("B40", this)

}
