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

case class CP263(value: Option[Int]) extends CtBoxIdentifier("Post reform losses brought forward and deducted") with CtOptionalInteger

object CP263 extends Calculated[CP263, ComputationsBoxRetriever] with CtTypeConverters {
  override def calculate(boxRetriever: ComputationsBoxRetriever): CP263 = {
    CP263(
      (boxRetriever.cp283b().value, boxRetriever.cp997().value) match {
        case (None, None) => None
        case _ => Some(boxRetriever.cp283b() + boxRetriever.cp997())
      }
    )
  }
}
