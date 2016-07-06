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

package uk.gov.hmrc.ct.accounts.frs10x

import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC8001_1(value: Option[String]) extends CtBoxIdentifier(name = "Directors name 1") with CtOptionalString with Input with ValidatableBox[Frs10xAccountsBoxRetriever] {
  override def validate(boxRetriever: Frs10xAccountsBoxRetriever): Set[CtValidation] =
    validateOptionalStringByLength("AC8001_1", this, 0, 40) ++ validateOptionalStringByRegex("AC8001_1", this, validCoHoCharacters)
}
