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

case class CP982(value: Option[Int]) extends CtBoxIdentifier(name = "Expenses from Off-payroll working (IR35)") with CtOptionalInteger with Input with ValidatableBox[ComputationsBoxRetriever] {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
    validateZeroOrPositiveInteger(this),
      cp981Breakdown(this, boxRetriever)
    )

  }


  def cp981Breakdown(value: CtOptionalInteger, boxRetriever: ComputationsBoxRetriever): Set[CtValidation]= {
    val cp981Error = (boxRetriever.cp983().orZero - boxRetriever.cp981().orZero) - boxRetriever.cp980().orZero
    failIf(value.isPositive && value.orZero > cp981Error) {
      val cp981Positive = if(cp981Error < 0) 0 else cp981Error
      Set(CtValidation(Some("CP982"), "error.cp982.breakdown", Some(Seq(cp981Positive.toString))))
    } ()
  }
}

object CP982 {

  def apply(value: Int): CP982 = CP982(Some(value))
}
