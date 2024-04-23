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

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

case class E27(value: Option[Int]) extends CtBoxIdentifier("Value of any non-qualifying investments and loans") with CtOptionalInteger with Input with ValidatableBox[CT600EBoxRetriever] {
  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = {
    failIf (boxRetriever.e26().value.contains(CharityLoansAndInvestments.SomeLoansAndInvestments)) {
      value match {
        case None => Set(CtValidation(Some(id), s"error.$id.required"))
        case Some(x) if x == 0 => Set(CtValidation(Some(id), s"error.$id.required"))
        case Some(x) if x < 0 => Set(CtValidation(Some(id), s"error.$id.mustBePositive"))
        case _ => Set()
      }
    }
  }
}
