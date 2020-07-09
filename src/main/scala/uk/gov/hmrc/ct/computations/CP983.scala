/*
 * Copyright 2020 HM Revenue & Customs
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
import uk.gov.hmrc.ct.validation.TurnoverValidation

case class CP983(value: Option[Int]) extends CtBoxIdentifier(name = "Turnover from off-payroll working")
  with CtOptionalInteger
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with TurnoverValidation {

  val compsStartDate = { br: ComputationsBoxRetriever => br.cp1() }
  val compsEndDate = { br: ComputationsBoxRetriever => br.cp2() }

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    val cato24 = boxRetriever.cato24
    collectErrors(
      requiredErrorIf(cato24.value.getOrElse(false) && this.value.isEmpty),
      validateZeroOrPositiveInteger(this),
      validateHmrcTurnover(boxRetriever, compsStartDate, compsEndDate, errorSuffix = "", secondaryIncome = boxRetriever.cp7().value.getOrElse(0))
    )

  }
}

object CP983 {
  def apply(value: Int): CP983 = CP983(Some(value))
}
