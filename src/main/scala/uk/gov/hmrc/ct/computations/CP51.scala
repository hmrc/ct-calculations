/*
 * Copyright 2023 HM Revenue & Customs
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

case class CP51(value: Option[Int]) extends CtBoxIdentifier(name = "Net loss on sale of fixed assets") with CtOptionalInteger with Input with ValidatableBox[ComputationsBoxRetriever] {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = validateZeroOrPositiveInteger(this)

  //override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
//  val cp33 = boxRetriever.cp33().value

//  (cp33) match {

//    case Some(cp33Value) if (cp33Value < 0 && value.getOrElse(0)  < cp33Value) => Set(CtValidation(Some("CP51"), "error.CP51.must.be.less.then.CP33"))
//    case Some(cp33Value) if (cp33Value > 0 && value.getOrElse(0) != 0) => Set(CtValidation(Some("CP51"), "error.CP57.CP51.must.be.mutually.exclusive"))
//    case _ => validateZeroOrNegativeInteger(this)
//  }
//}
}
object CP51 {

  def apply(int: Int): CP51 = CP51(Some(int))

}
