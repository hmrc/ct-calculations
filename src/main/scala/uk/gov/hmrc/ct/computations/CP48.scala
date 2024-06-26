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

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP48(value: Option[Int]) extends CtBoxIdentifier(name = "Donations") with CtOptionalInteger with Input with ValidatableBox[ComputationsBoxRetriever] {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    val cp29 = boxRetriever.cp29().orZero
    collectErrors(
      validateZeroOrPositiveInteger(this),
      failIf(cp29 != this.orZero) {
        Set(CtValidation(Some("CP48"), "error.CP48.must.equal.CP29", Some(Seq(cp29.toString))))
      }
    )
  }

}

object CP48 {

  def apply(int: Int): CP48 = CP48(Some(int))

}
