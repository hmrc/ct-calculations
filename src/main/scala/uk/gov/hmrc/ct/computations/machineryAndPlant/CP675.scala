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

package uk.gov.hmrc.ct.computations.machineryAndPlant

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Input, SelfValidatableBox}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP675(value: Int) extends CtBoxIdentifier(name = "Expenditure on which you want to claim the super-deduction allowance") with CtInteger with Input with SelfValidatableBox[ComputationsBoxRetriever, Int] {
  override def validate(boxRetriever: ComputationsBoxRetriever) = {
    collectErrors(
      validateIntegerRange("CP675", this, 0, 999999)
    )
  }
}

object CP675 {

  def apply(value: Int): CP675 = CP675(value)

}