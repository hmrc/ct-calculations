/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.ct.ct600a.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.cato.time.DateHelper
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever

case class LPQ07(value: Option[LocalDate]) extends CtBoxIdentifier(name = "When do you plan to file your return?") with CtOptionalDate with Input with ValidatableBox[CT600ABoxRetriever] {

  override def validate(boxRetriever: CT600ABoxRetriever): Set[CtValidation] = {
    failIf(boxRetriever.lpq04().orFalse) {
      validateDateAsMandatory("LPQ07", this) ++
      validateDateAsBetweenInclusive("LPQ07", this, DateHelper.now(), DateHelper.now().plusYears(2))
    }
  }

}
