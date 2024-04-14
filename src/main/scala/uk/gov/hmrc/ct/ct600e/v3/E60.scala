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

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

case class E60(value: Option[Int]) extends CtBoxIdentifier("Income UK land and buildings – excluding any amounts included on form CT600") with CtOptionalInteger with Input with ValidatableBox[CT600EBoxRetriever] {
  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    (e100(), e60()) match {
      case (_, e60) if e60.orZero < 0 => Set(CtValidation(boxId = Some("E60"), errorMessageKey = "error.E60.mustBeZeroOrPositive"))
      case (E100(Some(e100)), e60) if e100 > 0 && e60.orZero <= 0 => Set(CtValidation(boxId = Some("E60"), errorMessageKey = "error.E60.must.be.positive.when.E100.positive"))
      case _ => Set.empty
    }
  }
}
