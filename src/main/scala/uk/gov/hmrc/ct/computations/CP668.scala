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

case class CP668(value: Option[Int]) extends CtBoxIdentifier(name = "Writing down allowance claimed from special rate pool") with CtOptionalInteger with Input with ComputationValidatableBox[ComputationsBoxRetriever] with MachineryAndPlantCalculator {
  override def validate(boxRetriever: ComputationsBoxRetriever) = {
    validateZeroOrPositiveInteger(this) ++
      mandatoryIfCompanyIsTrading(boxRetriever, "CP668", value) ++
      specialRatePoolClaimedNotGreaterThanMaxSpecialPool(boxRetriever)
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
    val writtenDownValueOfSpecialRatePoolBroughtForward: Int = retriever.retrieveCP666().orZero
    val proceedsFromDisposalsFromSpecialRatePool: Int = retriever.retrieveCP667().orZero
    val specialRatePoolPercentage: BigDecimal = retriever.retrieveCATO22().value
    val cpAux3 = retriever.retrieveCPAux3().value

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
