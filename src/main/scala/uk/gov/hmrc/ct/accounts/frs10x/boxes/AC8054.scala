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

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC8054(value: Option[String]) extends CtBoxIdentifier(name = "Company policy on disabled employees") with CtOptionalString with Input with ValidatableBox[Frs10xDirectorsBoxRetriever] {
  override def validate(boxRetriever: Frs10xDirectorsBoxRetriever): Set[CtValidation] =
    validateOptionalStringByLength("AC8054", this, 0, StandardCohoTextFieldLimit) ++ validateCoHoStringReturnIllegalChars("AC8054", this)
}
