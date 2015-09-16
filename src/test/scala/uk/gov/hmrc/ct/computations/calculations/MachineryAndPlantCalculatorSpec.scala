package uk.gov.hmrc.ct.computations.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.CATO20
import uk.gov.hmrc.ct.computations._

class   MachineryAndPlantCalculatorSpec extends WordSpec with Matchers {

  "computeBalanceAllowance CP90" should {
    "return Balance of zero for all zero values" in new MachineryAndPlantCalculator {
      computeBalanceAllowance(cpq8 = CPQ8(value = Some(true)),
                              cp78 = CP78(0),
                              cp81 = CP81(0),
                              cp82 = CP82(0),
                              cp84 = CP84(0),
                              cp91 = CP91Input(None)) shouldBe CP90(Some(0))
    }
    "return None for negative disposal proceeds > zero and 0 for all others" in new MachineryAndPlantCalculator {
      computeBalanceAllowance(cpq8 = CPQ8(Some(true)),
                              cp78 = CP78(0),
                              cp81 = CP81(0),
                              cp82 = CP82(0),
                              cp84 = CP84(10),
                              cp91 = CP91Input(None)) shouldBe CP90(None)
    }
    "return None for disposal proceeds > zero and other fields add up to equals disposal proceeds" in new MachineryAndPlantCalculator {
      computeBalanceAllowance(cpq8 = CPQ8(value = Some(true)),
                              cp78 = CP78(10),
                              cp81 = CP81(0),
                              cp82 = CP82(0),
                              cp84 = CP84(10),
                              cp91 = CP91Input(None)) shouldBe CP90(Some(0))
    }
    "return Some Int for disposal proceeds > zero and other fields add up to more  disposal proceeds" in new MachineryAndPlantCalculator {
      computeBalanceAllowance(cpq8 = CPQ8(value = Some(true)),
                              cp78 = CP78(10),
                              cp81 = CP81(10),
                              cp82 = CP82(20),
                              cp84 = CP84(10),
                              cp91 = CP91Input(None)) shouldBe CP90(Some(30))
    }
    "return None disposal proceeds > zero and other fields add up to more disposal proceeds but ceased trading is false" in new MachineryAndPlantCalculator {
      computeBalanceAllowance(cpq8 = CPQ8(value = Some(false)),
                              cp78 = CP78(10),
                              cp81 = CP81(10),
                              cp82 = CP82(20),
                              cp84 = CP84(10),
                              cp91 = CP91Input(Some(123))) shouldBe CP90(None)
    }
  }

  "computeBalancingCharge CP91" should {
    "return None for all zero values" in new MachineryAndPlantCalculator {
      computeBalancingCharge(cpq8 = CPQ8(value = Some(true)),
                             cp78 = CP78(0),
                             cp81 = CP81(0),
                             cp82 = CP82(0),
                             cp84 = CP84(0),
                             cp91 = CP91Input(None)) shouldBe CP91(None)
    }
    "return Some(10) for negative disposal proceeds == 10 and 0 for all others" in new MachineryAndPlantCalculator {
      computeBalancingCharge(cpq8 = CPQ8(value = Some(true)),
                             cp78 = CP78(0),
                             cp81 = CP81(0),
                             cp82 = CP82(0),
                             cp84 = CP84(10),
                             cp91 = CP91Input(None)) shouldBe CP91(Some(10))
    }
    "return None for disposal proceeds > zero and other fields add up to equals disposal proceeds" in new MachineryAndPlantCalculator {
      computeBalancingCharge(cpq8 = CPQ8(value = Some(true)),
                             cp78 = CP78(10),
                             cp81 = CP81(0),
                             cp82 = CP82(0),
                             cp84 = CP84(10),
                             cp91 = CP91Input(None)) shouldBe CP91(None)
    }
    "return None for disposal proceeds > zero and other fields add up to more disposal proceeds" in new MachineryAndPlantCalculator {
      computeBalancingCharge(cpq8 = CPQ8(value = Some(true)),
                             cp78 = CP78(10),
                             cp81 = CP81(10),
                             cp82 = CP82(20),
                             cp84 = CP84(10),
                             cp91 = CP91Input(None)) shouldBe CP91(None)
    }
    "return Some value when disposal proceeds > sum of other fields and ceased trading is true" in new MachineryAndPlantCalculator {
      computeBalancingCharge(cpq8 = CPQ8(value = Some(true)),
                             cp78 = CP78(10),
                             cp81 = CP81(10),
                             cp82 = CP82(20),
                             cp84 = CP84(300),
                             cp91 = CP91Input(None)) shouldBe CP91(Some(260))
    }
    "return balancing charge entered by user when ceased trading is false" in new MachineryAndPlantCalculator {
      val userEnteredBalancingCharge = Some(9878)
      computeBalancingCharge(cpq8 = CPQ8(value = Some(false)),
                             cp91 = CP91Input(userEnteredBalancingCharge),
                             cp78 = CP78(10),
                             cp81 = CP81(10),
                             cp82 = CP82(20),
                             cp84 = CP84(300)) shouldBe CP91(userEnteredBalancingCharge)
    }
  }

  "computesBalances" should {
    "return Balance with allowance of zero and charge of None if ceasedTrading is true and total is zero" in new MachineryAndPlantCalculator {
      computesBalances(cpq8 = CPQ8(value = Some(true)),
                       cp78 = CP78(0),
                       cp81 = CP81(0),
                       cp82 = CP82(0),
                       cp84 = CP84(0),
                       cp91 = CP91Input(None)) shouldBe BalancesResult(CP90(Some(0)), CP91(None))
    }
    "return Balance with allowance of +ve and charge of None if ceasedTrading is true and total is +ve" in new MachineryAndPlantCalculator {
      computesBalances(cpq8 = CPQ8(value = Some(true)),
        cp78 = CP78(0),
        cp81 = CP81(10),
        cp82 = CP82(0),
        cp84 = CP84(0),
        cp91 = CP91Input(None)) shouldBe BalancesResult(CP90(Some(10)), CP91(None))
    }
    "return Balance allowance of None and charge of Some if ceasedTrading is true and total is -ve" in new MachineryAndPlantCalculator {
      computesBalances(cpq8 = CPQ8(value = Some(true)),
        cp78 = CP78(0),
        cp81 = CP81(0),
        cp82 = CP82(0),
        cp84 = CP84(10),
        cp91 = CP91Input(None)) shouldBe BalancesResult(CP90(None), CP91(Some(10)))
    }
    "return Balance allowance of None and charge of Some if ceasedTrading is false and userDefinedBalancingCharge is defined" in new MachineryAndPlantCalculator {
      val userEnteredBalancingCharge = Some(9878)
      computesBalances(
        cpq8 = CPQ8(value = Some(false)),
        cp78 = CP78(0),
        cp81 = CP81(0),
        cp82 = CP82(0),
        cp84 = CP84(10),
        cp91 = CP91Input(userEnteredBalancingCharge)
      ) shouldBe BalancesResult(CP90(None), CP91(userEnteredBalancingCharge))
    }
  }

  "computeTotalAllowances CP186" should {
    "return zero for all zero values when ceasedTrading is false" in new MachineryAndPlantCalculator {
      computeTotalAllowancesClaimed(cpq8 = CPQ8(value = Some(false)),
        cp87 = CP87(0),
        cp88 = CP88(0),
        cp89 = CP89(0),
        cp90 = CP90(None)) shouldBe CP186(Some(0))
    }
    "return Some(10) for totalFirstYearAllowanceClaimed == 10 and 0 for all others when ceasedTrading is false" in new MachineryAndPlantCalculator {
      computeTotalAllowancesClaimed(cpq8 = CPQ8(value = Some(false)),
        cp87 = CP87(10),
        cp88 = CP88(0),
        cp89 = CP89(0),
        cp90 = CP90(None)) shouldBe CP186(Some(10))
    }
    "return Some(125) from all but write down allowances entered when ceasedTrading is false" in new MachineryAndPlantCalculator {
      computeTotalAllowancesClaimed(cpq8 = CPQ8(value = Some(false)),
        cp87 = CP87(10),
        cp88 = CP88(115),
        cp89 = CP89(0),
        cp90 = CP90(None)) shouldBe CP186(Some(125))
    }
    "return Some(125) from all values entered when ceasedTrading is false" in new MachineryAndPlantCalculator {
      computeTotalAllowancesClaimed(cpq8 = CPQ8(value = Some(false)),
        cp87 = CP87(10),
        cp88 = CP88(100),
        cp89 = CP89(15),
        cp90 = CP90(None)) shouldBe CP186(Some(125))
    }
    "return None when ceasedTrading is false and balance allowance is not defined" in new MachineryAndPlantCalculator {
      computeTotalAllowancesClaimed(cpq8 = CPQ8(value = Some(true)),
        cp87 = CP87(10),
        cp88 = CP88(100),
        cp89 = CP89(15),
        cp90 = CP90(None)) shouldBe CP186(Some(0))
    }
    "return balanceAllowance when trading ceased is true and balance allowance is defined" in new MachineryAndPlantCalculator {//DONE
      computeTotalAllowancesClaimed(cpq8 = CPQ8(value = Some(true)),
        cp87 = CP87(10),
        cp88 = CP88(100),
        cp89 = CP89(15),
        cp90 = CP90(Some(345))) shouldBe CP186(Some(345))
    }
    "return None when ceasedTrading is not defined" in new MachineryAndPlantCalculator {
      computeTotalAllowancesClaimed(cpq8 = CPQ8(value = None),
        cp87 = CP87(10),
        cp88 = CP88(100),
        cp89 = CP89(15),
        cp90 = CP90(None)) shouldBe CP186(None)
    }
  }

  "Check Balance Allowance Charges Parameters" should {
    "perform calculation when ceased trading is true and userEnteredBalancingCharge is not defined" in new MachineryAndPlantCalculator {
      computesBalances(cpq8 = CPQ8(value = Some(true)),
        cp91 = CP91Input(None),
        cp78 = CP78(1),
        cp81 = CP81(2),
        cp82 = CP82(3),
        cp84 = CP84(4)
      )
    }
    "perform calculation when ceased trading is false and userEnteredBalancingCharge is defined" in new MachineryAndPlantCalculator {
      computesBalances(cpq8 = CPQ8(value = Some(false)),
        cp91 = CP91Input(Some(1234)),
        cp78 = CP78(1),
        cp81 = CP81(2),
        cp82 = CP82(3),
        cp84 = CP84(4)
      )
    }
    "perform calculation when ceased trading is not defined" in new MachineryAndPlantCalculator {
      computesBalances(cpq8 = CPQ8(value = None),
        cp91 = CP91Input(Some(1234)),
        cp78 = CP78(1),
        cp81 = CP81(2),
        cp82 = CP82(3),
        cp84 = CP84(4)
      )
    }
    "throw an IllegalArgumentException when ceased trading is true and userEnteredBalancingCharge is defined" in new MachineryAndPlantCalculator {
      an [IllegalArgumentException] should be thrownBy {
        computesBalances(cpq8 = CPQ8(value = Some(true)),
                          cp91 = CP91Input(Some(1234)),
                          cp78 = CP78(1),
                          cp81 = CP81(2),
                          cp82 = CP82(3),
                          cp84 = CP84(4))
      }
    }
  }

  "CP92 - Written Down Value calculation" should {
    "calculate the value if cease trading (CPQ8) is false and machinery and plant (CPQ10) is true" in new MachineryAndPlantCalculator {
      writtenDownValue(
        cpq8 = CPQ8(Some(false)),
        cpq10 = CPQ10(Some(true)),
        cp78 = CP78(100),
        cp81 = CP81(200),
        cp82 = CP82(300),
        cp84 = CP84(50),
        cp186 = CP186(Some(25)),
        cp91 = CP91(Some(400))
      ) should be (CP92(Some(925)))
    }
    "return 0 if cease trading (CPQ8) is true and machinery and plant (CPQ10) is true" in new MachineryAndPlantCalculator {
      writtenDownValue(
        cpq8 = CPQ8(Some(true)),
        cpq10 = CPQ10(Some(true)),
        cp78 = CP78(100),
        cp81 = CP81(200),
        cp82 = CP82(300),
        cp84 = CP84(50),
        cp186 = CP186(Some(25)),
        cp91 = CP91(Some(400))
      ) should be (CP92(Some(0)))
    }
    "return None if machinery and plant (CPQ10) is false (and any combination of cease trading CPQ8)" in new MachineryAndPlantCalculator {
      writtenDownValue(
        cpq8 = CPQ8(Some(true)),
        cpq10 = CPQ10(Some(false)),
        cp78 = CP78(100),
        cp81 = CP81(200),
        cp82 = CP82(300),
        cp84 = CP84(50),
        cp186 = CP186(Some(25)),
        cp91 = CP91(Some(400))
      ) should be (CP92(None))

      writtenDownValue(
        cpq8 = CPQ8(Some(false)),
        cpq10 = CPQ10(Some(false)),
        cp78 = CP78(100),
        cp81 = CP81(200),
        cp82 = CP82(300),
        cp84 = CP84(50),
        cp186 = CP186(Some(25)),
        cp91 = CP91(Some(400))
      ) should be (CP92(None))
    }
    "return None if machinery and plant (CPQ10) is not defined" in new MachineryAndPlantCalculator {
      writtenDownValue(
        cpq8 = CPQ8(None),
        cpq10 = CPQ10(Some(true)),
        cp78 = CP78(100),
        cp81 = CP81(200),
        cp82 = CP82(300),
        cp84 = CP84(50),
        cp186 = CP186(Some(25)),
        cp91 = CP91(Some(400))
      ) should be (CP92(None))
    }
    "return None if ceased trading (CPQ8) is not defined"in new MachineryAndPlantCalculator {
      writtenDownValue(
        cpq8 = CPQ8(Some(true)),
        cpq10 = CPQ10(None),
        cp78 = CP78(100),
        cp81 = CP81(200),
        cp82 = CP82(300),
        cp84 = CP84(50),
        cp186 = CP186(Some(25)),
        cp91 = CP91(Some(400))
      ) should be (CP92(None))
    }
  }

  "CATO20 - UnclaimedAIA_FYA calculation" should {
    "calculate the value correctly" in new MachineryAndPlantCalculator {
      unclaimedAIAFirstYearAllowance(
        cp81 = CP81(1),
        cp83 = CP83(Some(2)),
        cp87 = CP87(3),
        cp88 = CP88(Some(4)),
        cpAux1 = CPAux1(5)
      ) should be (CATO20(1))
    }
  }
}
