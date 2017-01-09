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

package uk.gov.hmrc.ct.computations.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.CATO20
import uk.gov.hmrc.ct.computations.CP92._
import uk.gov.hmrc.ct.computations._

class MachineryAndPlantCalculatorSpec extends WordSpec with Matchers {

  "computeBalanceAllowance CP90" should {

    "calculate Balance Allowance" in new MachineryAndPlantCalculator {
      computeBalanceAllowance(cpq8 = CPQ8(Some(true)),
                              cp78 = CP78(1),
                              cp666 = CP666(2),
                              cp674 = CP674(3),
                              cp84 = CP84(4),
                              cpAux1 = CPAux1(5),
                              cpAux2 = CPAux2(6),
                              cpAux3 = CPAux3(7),
                              cp673 = CP673(8)) shouldBe CP90(Some(12))
    }

    "calculate Balance Allowance where cpq8 is true and calculation < 0" in new MachineryAndPlantCalculator {
      computeBalanceAllowance(cpq8 = CPQ8(Some(true)),
                              cp78 = CP78(1),
                              cp666 = CP666(2),
                              cp674 = CP674(3),
                              cp84 = CP84(4),
                              cpAux1 = CPAux1(5),
                              cpAux2 = CPAux2(6),
                              cpAux3 = CPAux3(7),
                              cp673 = CP673(20)) shouldBe CP90(Some(0))
    }

    "calculate Balance Allowance where cpq8 is false" in new MachineryAndPlantCalculator {
      computeBalanceAllowance(cpq8 = CPQ8(Some(false)),
                              cp78 = CP78(1),
                              cp666 = CP666(2),
                              cp674 = CP674(3),
                              cp84 = CP84(4),
                              cpAux1 = CPAux1(5),
                              cpAux2 = CPAux2(6),
                              cpAux3 = CPAux3(7),
                              cp673 = CP673(8)) shouldBe CP90(None)
    }
  }

  "computeBalancingCharge CP91" should {
    "calculate CP91 using cpq8 = true, negative balancing charge" in new MachineryAndPlantCalculator {
      computeBalancingCharge(cpq8 = CPQ8(Some(true)),
                            cp78 = CP78(1),
                            cp666 = CP666(2),
                            cp674 = CP674(7),
                            cp84 = CP84(30),
                            cpAux1 = CPAux1(9),
                            cpAux2 = CPAux2(10),
                            cpAux3 = CPAux3(11),
                            cp667 = CP667(12),
                            cp673 = CP673(8),
                            cp672 = CP672(0),
                            cp82 = CP82(4),
                            cato20 = CATO20(0)) shouldBe CP91(Some(10))
    }


    "calculate CP91 using cpq8 = true, positive balancing charge" in new MachineryAndPlantCalculator {
      computeBalancingCharge( cpq8 = CPQ8(Some(true)),
                              cp78 = CP78(1),
                              cp666 = CP666(2),
                              cp674 = CP674(7),
                              cp84 = CP84(10),
                              cpAux1 = CPAux1(9),
                              cpAux2 = CPAux2(10),
                              cpAux3 = CPAux3(11),
                              cp667 = CP667(12),
                              cp673 = CP673(8),
                              cp672 = CP672(0),
                              cp82 = CP82(4),
                              cato20 = CATO20(0)) shouldBe CP91(Some(0))
    }

    "calculate CP91 using cpq8 = false, cp672 > val1" in new MachineryAndPlantCalculator {
      computeBalancingCharge( cpq8 = CPQ8(Some(false)),
                              cp78 = CP78(1),
                              cp666 = CP666(2),
                              cp674 = CP674(7),
                              cp84 = CP84(30),
                              cpAux1 = CPAux1(9),
                              cpAux2 = CPAux2(10),
                              cpAux3 = CPAux3(11),
                              cp667 = CP667(12),
                              cp673 = CP673(8),
                              cp672 = CP672(29),
                              cp82 = CP82(4),
                              cato20 = CATO20(13)) shouldBe CP91(Some(1))
    }

    "calculate CP91 using cpq8 = false, cp672 <= val1" in new MachineryAndPlantCalculator {
      computeBalancingCharge( cpq8 = CPQ8(Some(false)),
                              cp78 = CP78(1),
                              cp666 = CP666(2),
                              cp674 = CP674(7),
                              cp84 = CP84(30),
                              cpAux1 = CPAux1(9),
                              cpAux2 = CPAux2(10),
                              cpAux3 = CPAux3(11),
                              cp667 = CP667(12),
                              cp673 = CP673(8),
                              cp672 = CP672(28),
                              cp82 = CP82(4),
                              cato20 = CATO20(13)) shouldBe CP91(None)
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
      computeTotalAllowancesClaimed(cpq8 = CPQ8(Some(true)),
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

  "CP92 - Written Down Value calculation" should {

    "calculate CP92 using cpq8 = false and CP91 is null, value is -ve" in new MachineryAndPlantCalculator {
      writtenDownValue( cpq8 = CPQ8(Some(false)),
                        cp78 = CP78(Some(5)),
                        cp82 = CP82(6),
                        cp89 = CP89(7),
                        cp91 = CP91(None),
                        cp672 = CP672(9999),
                        cato20 = CATO20(8),
                        cpAux2 = CPAux2(7)) shouldBe CP92(Some(0))
    }

    "calculate CP92 using cpq8 = false and CP91 is null, value is +ve" in new MachineryAndPlantCalculator {
      writtenDownValue( cpq8 = CPQ8(Some(false)),
                        cp78 = CP78(Some(5)),
                        cp82 = CP82(6),
                        cp89 = CP89(7),
                        cp91 = CP91(None),
                        cp672 = CP672(0),
                        cato20 = CATO20(8),
                        cpAux2 = CPAux2(7)) shouldBe CP92(Some(19))
    }

    "calculate CP92 using cpq8 = true and CP91 is null, value is +ve" in new MachineryAndPlantCalculator {
      writtenDownValue( cpq8 = CPQ8(Some(true)),
                        cp78 = CP78(None),
                        cp82 = CP82(None),
                        cp89 = CP89(None),
                        cp91 = CP91(None),
                        cp672 = CP672(None),
                        cato20 = CATO20(0),
                        cpAux2 = CPAux2(0)) shouldBe CP92(Some(0))
    }

    "calculate CP92 using cpq8 = false and CP91 is not null, value is +ve" in new MachineryAndPlantCalculator {
      writtenDownValue( cpq8 = CPQ8(Some(false)),
        cp78 = CP78(None),
        cp82 = CP82(None),
        cp89 = CP89(None),
        cp91 = CP91(Some(91)),
        cp672 = CP672(None),
        cato20 = CATO20(0),
        cpAux2 = CPAux2(0)) shouldBe CP92(Some(0))
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
