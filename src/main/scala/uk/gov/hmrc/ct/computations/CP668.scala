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

case class CP668(value: Option[Int]) extends CtBoxIdentifier(name = "Writing down allowance claimed from special rate pool") with CtOptionalInteger with Input with ComputationValidatableBox[ComputationsBoxRetriever] with MachineryAndPlantCalculator {
  override def validate(boxRetriever: ComputationsBoxRetriever) = {
    cannotExistIf(hasValue && boxRetriever.cpQ8().isTrue) ++
    specialRatePoolAllowanceRequired(boxRetriever) ++
    specialRatePoolClaimedNotGreaterThanMaxSpecialPool(boxRetriever)
  }

  private def specialRatePoolAllowanceRequired(retriever: ComputationsBoxRetriever): Set[CtValidation] = {
    val cp666: Int = retriever.cp666().orZero
    val cp667: Int = retriever.cp667().orZero
    val cpAux3 = retriever.cpAux3().value

    (retriever.cpQ8().value, value) match {
      case (Some(false), None) if cpAux3 + cp666 > cp667 => Set(CtValidation(Some("CP668"), "error.CP668.specialRatePoolAllowanceRequired"))
      case (Some(false), Some(_)) if cpAux3 + cp666 > cp667 => validateZeroOrPositiveInteger(this)
      case _ => Set.empty
    }
  }

  private def specialRatePoolClaimedNotGreaterThanMaxSpecialPool(retriever: ComputationsBoxRetriever): Set[CtValidation] = {
    val maxSP = Math.max(0, calcSpecialRateAllowance(retriever))

    value match {
      case Some(writingDownAllowanceClaimedFromSpecialRatePool) if writingDownAllowanceClaimedFromSpecialRatePool > maxSP =>
        Set(CtValidation(boxId = Some("CP668"), errorMessageKey = "error.CP668.specialRatePoolAllowanceExceeded", Some(Seq(maxSP.toString))))
      case _ =>
        Set()
    }
  }

  private def calcSpecialRateAllowance(retriever: ComputationsBoxRetriever): Int = {
    val writtenDownValueOfSpecialRatePoolBroughtForward: Int = retriever.cp666().orZero
    val proceedsFromDisposalsFromSpecialRatePool: Int = retriever.cp667().orZero
    val specialRatePoolPercentage: BigDecimal = retriever.cato22().value
    val cpAux3 = retriever.cpAux3().value

    val allowance: BigDecimal = specialRatePoolPercentage * (
      writtenDownValueOfSpecialRatePoolBroughtForward
        + cpAux3
        - proceedsFromDisposalsFromSpecialRatePool
      ) / BigDecimal(100.0)

    allowance.setScale(0, RoundingMode.UP).toInt
  }
}

object CP668 {

  def apply(value: Int): CP668 = CP668(Some(value))
}
