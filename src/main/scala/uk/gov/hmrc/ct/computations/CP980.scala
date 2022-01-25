/*
 * Copyright 2022 HM Revenue & Customs
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

case class CP980(value: Option[Int]) extends CtBoxIdentifier(name = "Remuneration from Off-payroll working (IR35)")
  with CtOptionalInteger
  with Input
  with ValidatableBox[ComputationsBoxRetriever] {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateZeroOrPositiveInteger(this),
      exceedsMax(this.value, 999999),
      cp980Breakdown(this, boxRetriever)

    )

  }

  def cp980Breakdown(value: CtOptionalInteger, boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    val cp980Error = boxRetriever.cp983().orZero - boxRetriever.cp981().orZero
    failIf(value.orZero > cp980Error) {
    val cp980Positive = if(cp980Error < 0) 0 else cp980Error
    Set(CtValidation(Some("CP980"), "error.cp980.breakdown", Some(Seq(cp980Positive.toString))))
  }
  }
}



object CP980 {

  def apply(int: Int): CP980 = CP980(Some(int))


}

