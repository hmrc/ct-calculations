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

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.Validators.TradingLossesValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.domain.ValidationConstants._

case class CP281(value: Option[Int]) extends CtBoxIdentifier("Losses brought forward")
  with CtOptionalInteger
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with TradingLossesValidation {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    (boxRetriever.retrieveCPQ17().value, value) match {
      case (None, Some(_)) => Set(CtValidation(Some("CP281"), "error.CP281.cannot.exist"))
      case (Some(true), None) => Set(CtValidation(Some("CP281"), "error.CP281.required"))
      case (Some(false), Some(_)) => Set(CtValidation(Some("CP281"), "error.CP281.cannot.exist"))
      case (Some(true), Some(losses)) if losses < 1 => Set(CtValidation(Some("CP281"), "error.CP281.below.min"))
      case (Some(true), Some(losses)) if losses > MAX_MONEY_AMOUNT_ALLOWED => Set(CtValidation(Some("CP281"), "error.CP281.exceeds.max"))
      case _ => Set.empty
    }
  }
}

object CP281 {

  def apply(int: Int): CP281 = CP281(Some(int))
}
