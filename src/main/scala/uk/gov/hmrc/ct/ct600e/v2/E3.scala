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

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

case class E3(value: Option[Int]) extends CtBoxIdentifier("Further repayment") with CtOptionalInteger with Input with ValidatableBox[CT600EBoxRetriever] {

  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = {
    validateConditionalRequired(boxRetriever)
  }

  private def validateConditionalRequired(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = {
    val e1 = boxRetriever.e1().orZero
    val e2 = boxRetriever.e2().orZero

    value match {
      case None if e2 > e1 => Set(CtValidation(Some("E3"), "error.E3.conditionalRequired"))
      case Some(x) if e2 < e1 => Set(CtValidation(Some("E3"), "error.E3.conditionalMustBeEmpty"))
      case Some(x) if x < 1 => Set(CtValidation(Some("E3"), "error.E3.outOfRange"))
      case _ => Set()
    }
  }

}
