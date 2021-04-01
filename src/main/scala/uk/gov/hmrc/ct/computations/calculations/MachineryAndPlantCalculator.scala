/*
 * Copyright 2021 HM Revenue & Customs
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

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.CATO20
import uk.gov.hmrc.ct.box.{CtTypeConverters, CtValidation}
import uk.gov.hmrc.ct.computations._

trait MachineryAndPlantCalculator extends CtTypeConverters {

  def computeBalanceAllowance(cpq8: CPQ8,
                              cp78: CP78,
                              cp84: CP84,
                              cp666: CP666,
                              cp673: CP673,
                              cp674: CP674,
                              cpAux1: CPAux1,
                              cpAux2: CPAux2,
                              cpAux3: CPAux3): CP90 = {
    val varAux = cp78 + cp666 + cp674 - cp84 + cpAux1 + cpAux2 + cpAux3 - cp673

    val result = cpq8.value match {
      case Some(true) => Some(varAux max 0)
      case _ => None
    }

    CP90(result)
  }

  def computeBalancingCharge(cpq8: CPQ8,
                             cp78: CP78,
                             cp82: CP82,
                             cp84: CP84,
                             cp666: CP666,
                             cp667: CP667,
                             cp672: CP672,
                             cp673: CP673,
                             cp674: CP674,
                             cpAux1: CPAux1,
                             cpAux2: CPAux2,
                             cpAux3: CPAux3,
                             cato20: CATO20): CP91 = {
    val result: Option[Int] = cpq8.value match {
      case Some(true) => {
        val sum: Int = cp78 + cp666 + cp674 - cp84 - cp667 + cpAux1 + cpAux2 + cpAux3 - cp673
        Some(sum.min(0).abs)
      }
      case Some(false) => {
        val x: Int = cp78 + cp82 + cpAux2 + cato20
        if (cp672 > x)
          Some(cp672 - x)
        else None
      }
      case _ => None
    }

    CP91(result)
  }

  def computeTotalAllowancesClaimed(cpq8: CPQ8,
                                    cp87: CP87,
                                    cp88: CP88,
                                    cp89: CP89,
                                    cp90: CP90 = CP90(value = None)): CP186 = {
    val result = cpq8.value.flatMap {
      ceasedTrading =>
        if (!ceasedTrading) {
          Some(cp87 + cp88 + cp89)
        } else {
          Some(cp90.orZero)
        }
    }
    CP186(result)
  }

  def writtenDownValue(cpq8: CPQ8,
                       cp78: CP78,
                       cp79: CP79,
                       cp82: CP82,
                       cp83: CP83,
                       cp89: CP89,
                       cp91: CP91,
                       cp672: CP672,
                       cato20: CATO20,
                       cpAux1: CPAux1,
                       cpAux2: CPAux2): CP92 = {

    val totalMainPoolExpenditure: Int = cp78 + cp82 + cpAux2
    val totalFYAExpenditure: Int = cp79 + cpAux1
    val totalAIA: Int = cp83
    val totalFYAAndAIAClaimed: Int = cato20
    val mainPoolClaimed: Int = cp89
    val mainPoolDisposals: Int = cp672

    val result = (cpq8.value, cp91.value) match {
      case (Some(false), None) => (totalMainPoolExpenditure + totalFYAExpenditure + totalAIA
        - (totalFYAAndAIAClaimed + mainPoolClaimed + mainPoolDisposals))
        .max(0)
      case _ => 0
    }

    CP92(Some(result))
  }

  def unclaimedAIAFirstYearAllowance(cp87: CP87, cp88: CP88): CATO20 = {
    CATO20(cp87 + cp88)
  }

  def sumOfCP78AndCP666(cp78: CP78, cp666: CP666): Set[CtValidation] = {
    val totalValue = cp78 + cp666
    if (totalValue > 312000)
      Set(CtValidation(None, "error.sum.of.cp78cp666.exceeds.total"))
    else
      Set.empty
  }

  case class BalancesResult(cp90: CP90, cp91: CP91)

}