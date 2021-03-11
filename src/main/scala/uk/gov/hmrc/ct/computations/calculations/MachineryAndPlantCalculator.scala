/*
 * Copyright 2021 HM Revenue & Customs
 *
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
                       cp82: CP82,
                       cp89: CP89,
                       cp91: CP91,
                       cp672: CP672,
                       cato20: CATO20,
                       cpAux2: CPAux2): CP92 = {

    val result = (cpq8.value, cp91.value) match {
      case (Some(false), None) => (cp78 + cp82 + cpAux2 + cato20 - cp89 - cp672).max(0)
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