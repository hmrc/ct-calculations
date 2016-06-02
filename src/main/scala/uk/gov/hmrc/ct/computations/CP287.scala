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
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP287(value: Option[Int]) extends CtBoxIdentifier(name = "Amount of loss carried back to earlier periods")
  with CtOptionalInteger
  with Input
  with ValidatableBox[ComputationsBoxRetriever] {


  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._
    val max = retrieveCP118().value - retrieveCP998().orZero

    (value, retrieveCPQ20().value, max) match {
      case (None, Some(true), _) => Set(CtValidation(Some("CP287"), "error.CP287.required"))
      case (Some(lossCarriedBack), Some(true), limit) if lossCarriedBack > limit => Set(CtValidation(Some("CP287"), "error.CP287.exceeds.max"))
      case (Some(_), Some(false) | None, _) => Set(CtValidation(Some("CP287"), "error.CP287.cannot.exist"))

      case _ => Set.empty
    }
  }
}

object CP287 {

  def apply(int: Int): CP287 = CP287(Some(int))
}
