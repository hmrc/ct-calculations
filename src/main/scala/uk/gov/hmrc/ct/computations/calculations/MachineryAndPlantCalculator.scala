package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.CATO20
import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._

trait MachineryAndPlantCalculator extends CtTypeConverters {

  def computeBalanceAllowance(cpq8: CPQ8,
                              cp78: CP78,
                              cp81: CP81,
                              cp82: CP82,
                              cp84: CP84,
                              cp91: CP91Input): CP90 = {
    val result  = total(cpq8, cp78, cp81, cp82, cp84, cp91) match {
      case Some(t) if t >= 0 => Some(t)
      case _ => None
    }
    CP90(result)
  }

  def computeBalancingCharge(cpq8: CPQ8,
                             cp78: CP78,
                             cp81: CP81,
                             cp82: CP82,
                             cp84: CP84,
                             cp91: CP91Input): CP91 = {
    val result = total(cpq8, cp78, cp81, cp82, cp84, cp91) match {
      case Some(t) if t < 0 => Some(t.abs)
      case _ => cp91.value
    }
    CP91(result)
  }

  def computesBalances(cpq8: CPQ8,
                       cp78: CP78,
                       cp81: CP81,
                       cp82: CP82,
                       cp84: CP84,
                       cp91: CP91Input): BalancesResult = {
    (computeBalanceAllowance(cpq8, cp78, cp81, cp82, cp84, cp91), computeBalancingCharge(cpq8, cp78, cp81, cp82, cp84, cp91)) match {
      case (CP90(Some(allowance)), CP91(Some(charge))) =>
        throw new IllegalArgumentException("We should never be able to have both an allowance and a charge.")
      case (allowance, charge) => BalancesResult(allowance, charge)
    }
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
          Some(cp90.value.getOrElse(0))
        }
    }
    CP186(result)
  }

  def writtenDownValue(cpq8: CPQ8,
                       cpq10: CPQ10,
                       cp78: CP78,
                       cp81: CP81,
                       cp82: CP82,
                       cp84: CP84,
                       cp186: CP186,
                       cp91: CP91): CP92 = {

    def calculation(ceaseTrading: Boolean): Int = {
      if (ceaseTrading) 0
      else cp78 + cp81 + cp82 - cp84 - cp186 + cp91
    }

    val result = for{
      ceaseTrading <- cpq8.value
      machineryAndPlant <- cpq10.value if machineryAndPlant
    } yield calculation(ceaseTrading)
    CP92(result)
  }

  def unclaimedAIAFirstYearAllowance(cp81: CP81, cp83: CP83, cp87: CP87, cp88: CP88, cpAux1: CPAux1): CATO20 = {
    CATO20(cp81 + cpAux1 - cp87 + cp83 - cp88 )
  }

  private def total(cpq8: CPQ8,
                    cp78: CP78,
                    cp81: CP81,
                    cp82: CP82,
                    cp84: CP84,
                    cp91: CP91Input): Option[Int] = {
    checkParameters(cpq8, cp78, cp81, cp82, cp84, cp91)
    cpq8.value.flatMap(ceasedTrading => if (ceasedTrading) Some(calculation(cp78, cp81, cp82, cp84)) else None)
  }

  private def calculation(cp78: CP78,
                          cp81: CP81,
                          cp82: CP82,
                          cp84: CP84): Int = cp78.value.getOrElse(0) + cp81 + cp82.value.getOrElse(0) - cp84.value.getOrElse(0)

  private def checkParameters(cpq8: CPQ8,
                              cp78: CP78,
                              cp81: CP81,
                              cp82: CP82,
                              cp84: CP84,
                              cp91: CP91Input) {
  }

}

case class BalancesResult(cp90: CP90, cp91: CP91)