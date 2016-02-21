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

import uk.gov.hmrc.ct.box.{CtValidation, CtBoxIdentifier, CtOptionalInteger, Input}
import uk.gov.hmrc.ct.computations.Validators.ComputationValidatableBox
import uk.gov.hmrc.ct.computations.calculations.MachineryAndPlantCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

import scala.math.BigDecimal.RoundingMode

case class CP89(value: Option[Int]) extends CtBoxIdentifier(name = "Writing Down Allowance claimed from main pool") with CtOptionalInteger with Input with ComputationValidatableBox[ComputationsBoxRetriever] with MachineryAndPlantCalculator {
  override def validate(boxRetriever: ComputationsBoxRetriever) = {
    validateZeroOrPositiveInteger(this) ++
      mandatoryIfCompanyIsTrading(boxRetriever, "CP89", value) ++
      mainPoolClaimedNotGreaterThanMaxMainPool(boxRetriever)
  }

  private def mainPoolClaimedNotGreaterThanMaxMainPool(retriever: ComputationsBoxRetriever): Set[CtValidation] = {
    val maxAllowance: Int = Math.max(0, calcMainPoolAllowance(retriever))

    value match {
      case Some(writingDownAllowanceClaimedFromMainPool) if writingDownAllowanceClaimedFromMainPool > maxAllowance =>
        Set(CtValidation(boxId = Some("CP89"), errorMessageKey = "error.CP89.mainPoolAllowanceExceeded", Some(Seq(maxAllowance.toString))))
      case _ =>
        Set()
    }
  }

  private def calcMainPoolAllowance(retriever: ComputationsBoxRetriever): Int = {
    val unclaimedFYA_AIA = unclaimedAIAFirstYearAllowance(
      retriever.retrieveCP81(),
      retriever.retrieveCP83(),
      retriever.retrieveCP87(),
      retriever.retrieveCP88(),
      retriever.retrieveCPAux1())

    val cp78 = retriever.retrieveCP78().orZero
    val cp82 = retriever.retrieveCP82().orZero
    val cpAux2 = retriever.retrieveCPAux2()
    val cp672 = retriever.retrieveCP672().orZero
    val mainPoolPercentage: BigDecimal = retriever.retrieveCATO21().value

    val allowance: BigDecimal = mainPoolPercentage * (cp78 + cp82 + cpAux2 - cp672 + unclaimedFYA_AIA) / BigDecimal(100)

    allowance.setScale(0, RoundingMode.UP).toInt
  }
}

object CP89 {

  def apply(value: Int): CP89 = CP89(Some(value))
}
