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

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

case class E50(value: Option[Int]) extends CtBoxIdentifier("Income Total turnover from exempt charitable trading activities") with CtOptionalInteger with Input with ValidatableBox[CT600EBoxRetriever] {
  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = {
    import boxRetriever._
    (e95(), e50()) match {
      case (_, e50) if e50.orZero < 0 => Set(CtValidation(boxId = Some("E50"), errorMessageKey = "error.E50.mustBeZeroOrPositive"))
      case (E95(Some(e95)), e50) if e95 > 0 && e50.orZero <= 0 => Set(CtValidation(boxId = Some("E50"), errorMessageKey = "error.E50.must.be.positive.when.E95.positive"))
      case _ => Set.empty
    }
  }
}
