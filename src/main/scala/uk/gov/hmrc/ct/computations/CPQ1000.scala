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

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CPQ1000(value: Option[Boolean]) extends CtBoxIdentifier(name = "Did you buy any company cars in this period?") with CtOptionalBoolean with Input with ValidatableBox[ComputationsBoxRetriever] {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    (boxRetriever.cpQ7(), value) match {
      case (CPQ7(Some(true)), None) => validateBooleanAsMandatory("CPQ1000", this)
      case (CPQ7(Some(false)) | CPQ7(None), Some(_)) => Set(CtValidation(Some("CPQ1000"), "error.CPQ1000.cannot.exist.without.cpq7"))
      case _ => Set.empty
    }
  }
}
