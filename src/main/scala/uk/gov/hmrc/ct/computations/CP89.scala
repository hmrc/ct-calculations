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

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, CtValidation, Input}
import uk.gov.hmrc.ct.computations.Validators.ComputationValidatableBox
import uk.gov.hmrc.ct.computations.calculations.MachineryAndPlantCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

import scala.math.BigDecimal.RoundingMode

case class CP89(value: Option[Int]) extends CtBoxIdentifier(name = "Writing Down Allowance claimed from main pool") with CtOptionalInteger with Input with ComputationValidatableBox[ComputationsBoxRetriever] with MachineryAndPlantCalculator {
  override def validate(boxRetriever: ComputationsBoxRetriever) = {
      mainPoolAllowanceRequired(boxRetriever) ++
      mainPoolClaimedNotGreaterThanMaxMainPool(boxRetriever)
  }

  private def mainPoolAllowanceRequired(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    val cpq8 = boxRetriever.cpQ8().value
    val cpAux2 = boxRetriever.cpAux2().value
    val cp78 = boxRetriever.cp78().orZero
    val cp672 = boxRetriever.cp672().orZero

    (cpq8, value) match {
      case (Some(false), None) if cpAux2 + cp78 > cp672 => Set(CtValidation(Some("CP89"), "error.CP89.mainPoolAllowanceRequired"))
      case (Some(false), Some(_)) => validateZeroOrPositiveInteger(this)
      case _ => Set.empty
    }

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
      retriever.cp81(),
      retriever.cp83(),
      retriever.cp87(),
      retriever.cp88(),
      retriever.cpAux1())

    val cp78 = retriever.cp78().orZero
    val cp82 = retriever.cp82().orZero
    val cpAux2 = retriever.cpAux2()
    val cp672 = retriever.cp672().orZero
    val mainPoolPercentage: BigDecimal = retriever.cato21().value

    val allowance: BigDecimal = mainPoolPercentage * (cp78 + cp82 + cpAux2 - cp672 + unclaimedFYA_AIA) / BigDecimal(100)

    allowance.setScale(0, RoundingMode.UP).toInt
  }
}

object CP89 {

  def apply(value: Int): CP89 = CP89(Some(value))
}
