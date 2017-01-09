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

case class CP503(value: Option[Int]) extends CtBoxIdentifier(name = "Income from property expenses") with CtOptionalInteger with Input with ValidatableBox[ComputationsBoxRetriever] {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = validateZeroOrPositiveInteger(this) ++ validateNotExceedingCP501(this, boxRetriever)

  private def validateNotExceedingCP501(box: CP503, retriever: ComputationsBoxRetriever): Set[CtValidation] = {
    (box.value, retriever.cp501().value) match {
      case (Some(x), Some(y)) if x > y => Set(CtValidation(Some(box.id), s"error.${box.id}.propertyExpensesExceedsIncome"))
      case (Some(x), None) if x > 0 => Set(CtValidation(Some(box.id), s"error.${box.id}.propertyExpensesExceedsIncome"))
      case _ => Set()
    }
  }
}
