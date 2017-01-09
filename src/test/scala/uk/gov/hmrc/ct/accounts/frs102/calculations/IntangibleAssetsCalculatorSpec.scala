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

package uk.gov.hmrc.ct.accounts.frs102.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.boxes._

class IntangibleAssetsCalculatorSpec extends WordSpec with Matchers {

  "IntangibleAssetsCalculator" should {
    
    "calculating AC117A" when {
      "return None when all inputs are empty" in new IntangibleAssetsCalculator {
        calculateAC117A(AC114A(None), AC115A(None), AC116A(None), AC209A(None), AC210A(None)) shouldBe AC117A(None)
      }

      "return correct value if at least one input is non empty" in new IntangibleAssetsCalculator {
        calculateAC117A(AC114A(Some(1)), AC115A(None), AC116A(None), AC209A(None), AC210A(None)) shouldBe AC117A(Some(1))
        calculateAC117A(AC114A(None), AC115A(Some(1)), AC116A(None), AC209A(None), AC210A(None)) shouldBe AC117A(Some(1))
        calculateAC117A(AC114A(None), AC115A(None), AC116A(Some(1)), AC209A(None), AC210A(None)) shouldBe AC117A(Some(-1))
        calculateAC117A(AC114A(None), AC115A(None), AC116A(None), AC209A(Some(1)), AC210A(None)) shouldBe AC117A(Some(1))
        calculateAC117A(AC114A(None), AC115A(None), AC116A(None), AC209A(None), AC210A(Some(1))) shouldBe AC117A(Some(1))
      }

      "return correct positive value" in new IntangibleAssetsCalculator {
        calculateAC117A(AC114A(Some(1)), AC115A(Some(1)), AC116A(Some(1)), AC209A(Some(1)), AC210A(Some(1))) shouldBe AC117A(Some(3))
      }

      "return correct negative value" in new IntangibleAssetsCalculator {
        calculateAC117A(AC114A(Some(1)), AC115A(Some(1)), AC116A(Some(1)), AC209A(Some(-1)), AC210A(Some(-1))) shouldBe AC117A(Some(-1))
      }
    }

    "calculating AC117B" when {
      "return None when all inputs are empty" in new IntangibleAssetsCalculator {
        calculateAC117B(AC114B(None), AC115B(None), AC116B(None), AC209B(None), AC210B(None)) shouldBe AC117B(None)
      }

      "return correct value if at least one input is non empty" in new IntangibleAssetsCalculator {
        calculateAC117B(AC114B(Some(1)), AC115B(None), AC116B(None), AC209B(None), AC210B(None)) shouldBe AC117B(Some(1))
        calculateAC117B(AC114B(None), AC115B(Some(1)), AC116B(None), AC209B(None), AC210B(None)) shouldBe AC117B(Some(1))
        calculateAC117B(AC114B(None), AC115B(None), AC116B(Some(1)), AC209B(None), AC210B(None)) shouldBe AC117B(Some(-1))
        calculateAC117B(AC114B(None), AC115B(None), AC116B(None), AC209B(Some(1)), AC210B(None)) shouldBe AC117B(Some(1))
        calculateAC117B(AC114B(None), AC115B(None), AC116B(None), AC209B(None), AC210B(Some(1))) shouldBe AC117B(Some(1))
      }

      "return correct positive value" in new IntangibleAssetsCalculator {
        calculateAC117B(AC114B(Some(1)), AC115B(Some(1)), AC116B(Some(1)), AC209B(Some(1)), AC210B(Some(1))) shouldBe AC117B(Some(3))
      }

      "return correct negative value" in new IntangibleAssetsCalculator {
        calculateAC117B(AC114B(Some(1)), AC115B(Some(1)), AC116B(Some(1)), AC209B(Some(-1)), AC210B(Some(-1))) shouldBe AC117B(Some(-1))
      }
    }

    "calculating AC121A" when {
      "return None when all inputs are empty" in new IntangibleAssetsCalculator {
        calculateAC121A(AC118A(None), AC119A(None), AC120A(None), AC211A(None)) shouldBe AC121A(None)
      }

      "return correct value if at least one input is non empty" in new IntangibleAssetsCalculator {
        calculateAC121A(AC118A(Some(1)), AC119A(None), AC120A(None), AC211A(None)) shouldBe AC121A(Some(1))
        calculateAC121A(AC118A(None), AC119A(Some(1)), AC120A(None), AC211A(None)) shouldBe AC121A(Some(1))
        calculateAC121A(AC118A(None), AC119A(None), AC120A(Some(1)), AC211A(None)) shouldBe AC121A(Some(-1))
        calculateAC121A(AC118A(None), AC119A(None), AC120A(None), AC211A(Some(1))) shouldBe AC121A(Some(1))
      }

      "return correct positive value" in new IntangibleAssetsCalculator {
        calculateAC121A(AC118A(Some(1)), AC119A(Some(1)), AC120A(Some(1)), AC211A(Some(1))) shouldBe AC121A(Some(2))
      }

      "return correct negative value" in new IntangibleAssetsCalculator {
        calculateAC121A(AC118A(Some(0)), AC119A(Some(1)), AC120A(Some(1)), AC211A(Some(-1))) shouldBe AC121A(Some(-1))
      }
    }

    "calculating AC121B" when {
      "return None when all inputs are empty" in new IntangibleAssetsCalculator {
        calculateAC121B(AC118B(None), AC119B(None), AC120B(None), AC211B(None)) shouldBe AC121B(None)
      }

      "return correct value if at least one input is non empty" in new IntangibleAssetsCalculator {
        calculateAC121B(AC118B(Some(1)), AC119B(None), AC120B(None), AC211B(None)) shouldBe AC121B(Some(1))
        calculateAC121B(AC118B(None), AC119B(Some(1)), AC120B(None), AC211B(None)) shouldBe AC121B(Some(1))
        calculateAC121B(AC118B(None), AC119B(None), AC120B(Some(1)), AC211B(None)) shouldBe AC121B(Some(-1))
        calculateAC121B(AC118B(None), AC119B(None), AC120B(None), AC211B(Some(1))) shouldBe AC121B(Some(1))
      }

      "return correct positive value" in new IntangibleAssetsCalculator {
        calculateAC121B(AC118B(Some(1)), AC119B(Some(1)), AC120B(Some(1)), AC211B(Some(1))) shouldBe AC121B(Some(2))
      }

      "return correct negative value" in new IntangibleAssetsCalculator {
        calculateAC121B(AC118B(Some(0)), AC119B(Some(1)), AC120B(Some(1)), AC211B(Some(-1))) shouldBe AC121B(Some(-1))
      }
    }

    "calculating AC122A" when {
      "return None when all inputs are empty" in new IntangibleAssetsCalculator {
        calculateAC122A(AC117A(None), AC121A(None)) shouldBe AC122A(None)
      }

      "return correct value if at least one input is non empty" in new IntangibleAssetsCalculator {
        calculateAC122A(AC117A(Some(1)), AC121A(None)) shouldBe AC122A(Some(1))
        calculateAC122A(AC117A(None), AC121A(Some(1))) shouldBe AC122A(Some(-1))
      }

      "return correct positive value" in new IntangibleAssetsCalculator {
        calculateAC122A(AC117A(Some(2)), AC121A(Some(1))) shouldBe AC122A(Some(1))
      }

      "return correct negative value" in new IntangibleAssetsCalculator {
        calculateAC122A(AC117A(Some(0)), AC121A(Some(1))) shouldBe AC122A(Some(-1))
      }
    }

    "calculating AC122B" when {
      "return None when all inputs are empty" in new IntangibleAssetsCalculator {
        calculateAC122B(AC117B(None), AC121B(None)) shouldBe AC122B(None)
      }

      "return correct value if at least one input is non empty" in new IntangibleAssetsCalculator {
        calculateAC122B(AC117B(Some(1)), AC121B(None)) shouldBe AC122B(Some(1))
        calculateAC122B(AC117B(None), AC121B(Some(1))) shouldBe AC122B(Some(-1))
      }

      "return correct positive value" in new IntangibleAssetsCalculator {
        calculateAC122B(AC117B(Some(2)), AC121B(Some(1))) shouldBe AC122B(Some(1))
      }

      "return correct negative value" in new IntangibleAssetsCalculator {
        calculateAC122B(AC117B(Some(0)), AC121B(Some(1))) shouldBe AC122B(Some(-1))
      }
    }

    "calculating AC123A" when {
      "return None when all inputs are empty" in new IntangibleAssetsCalculator {
        calculateAC123A(AC114A(None), AC118A(None)) shouldBe AC123A(None)
      }

      "return correct value if at least one input is non empty" in new IntangibleAssetsCalculator {
        calculateAC123A(AC114A(Some(1)), AC118A(None)) shouldBe AC123A(Some(1))
        calculateAC123A(AC114A(None), AC118A(Some(1))) shouldBe AC123A(Some(-1))
      }

      "return correct positive value" in new IntangibleAssetsCalculator {
        calculateAC123A(AC114A(Some(2)), AC118A(Some(1))) shouldBe AC123A(Some(1))
      }

      "return correct negative value" in new IntangibleAssetsCalculator {
        calculateAC123A(AC114A(Some(0)), AC118A(Some(1))) shouldBe AC123A(Some(-1))
      }
    }

    "calculating AC114" when {
      "return None when all inputs are empty" in new IntangibleAssetsCalculator {
        calculateAC114(AC114A(None), AC114B(None)) shouldBe AC114(None)
      }

      "return correct value if at least one input is non empty" in new IntangibleAssetsCalculator {
        calculateAC114(AC114A(Some(1)), AC114B(None)) shouldBe AC114(Some(1))
        calculateAC114(AC114A(None), AC114B(Some(1))) shouldBe AC114(Some(1))
      }

      "return correct positive value" in new IntangibleAssetsCalculator {
        calculateAC114(AC114A(Some(2)), AC114B(Some(1))) shouldBe AC114(Some(3))
      }
    }

    "calculating AC115" when {
      "return None when all inputs are empty" in new IntangibleAssetsCalculator {
        calculateAC115(AC115A(None), AC115B(None)) shouldBe AC115(None)
      }

      "return correct value if at least one input is non empty" in new IntangibleAssetsCalculator {
        calculateAC115(AC115A(Some(1)), AC115B(None)) shouldBe AC115(Some(1))
        calculateAC115(AC115A(None), AC115B(Some(1))) shouldBe AC115(Some(1))
      }

      "return correct positive value" in new IntangibleAssetsCalculator {
        calculateAC115(AC115A(Some(2)), AC115B(Some(1))) shouldBe AC115(Some(3))
      }
    }

    "calculating AC116" when {
      "return None when all inputs are empty" in new IntangibleAssetsCalculator {
        calculateAC116(AC116A(None), AC116B(None)) shouldBe AC116(None)
      }

      "return correct value if at least one input is non empty" in new IntangibleAssetsCalculator {
        calculateAC116(AC116A(Some(1)), AC116B(None)) shouldBe AC116(Some(1))
        calculateAC116(AC116A(None), AC116B(Some(1))) shouldBe AC116(Some(1))
      }

      "return correct positive value" in new IntangibleAssetsCalculator {
        calculateAC116(AC116A(Some(2)), AC116B(Some(1))) shouldBe AC116(Some(3))
      }
    }

    "calculating AC209" when {
      "return None when all inputs are empty" in new IntangibleAssetsCalculator {
        calculateAC209(AC209A(None), AC209B(None)) shouldBe AC209(None)
      }

      "return correct value if at least one input is non empty" in new IntangibleAssetsCalculator {
        calculateAC209(AC209A(Some(1)), AC209B(None)) shouldBe AC209(Some(1))
        calculateAC209(AC209A(None), AC209B(Some(1))) shouldBe AC209(Some(1))
      }

      "return correct positive value" in new IntangibleAssetsCalculator {
        calculateAC209(AC209A(Some(2)), AC209B(Some(1))) shouldBe AC209(Some(3))
      }
    }

    "calculating AC210" when {
      "return None when all inputs are empty" in new IntangibleAssetsCalculator {
        calculateAC210(AC210A(None), AC210B(None)) shouldBe AC210(None)
      }

      "return correct value if at least one input is non empty" in new IntangibleAssetsCalculator {
        calculateAC210(AC210A(Some(1)), AC210B(None)) shouldBe AC210(Some(1))
        calculateAC210(AC210A(None), AC210B(Some(1))) shouldBe AC210(Some(1))
      }

      "return correct positive value" in new IntangibleAssetsCalculator {
        calculateAC210(AC210A(Some(2)), AC210B(Some(1))) shouldBe AC210(Some(3))
      }
    }

    "calculating AC117" when {
      "return None when all inputs are empty" in new IntangibleAssetsCalculator {
        calculateFullAC117(AC117A(None), AC117B(None)) shouldBe AC117(None)
      }

      "return correct value if at least one input is non empty" in new IntangibleAssetsCalculator {
        calculateFullAC117(AC117A(Some(1)), AC117B(None)) shouldBe AC117(Some(1))
        calculateFullAC117(AC117A(None), AC117B(Some(1))) shouldBe AC117(Some(1))
      }

      "return correct positive value" in new IntangibleAssetsCalculator {
        calculateFullAC117(AC117A(Some(2)), AC117B(Some(1))) shouldBe AC117(Some(3))
      }
    }

    "calculating AC118" when {
      "return None when all inputs are empty" in new IntangibleAssetsCalculator {
        calculateAC118(AC118A(None), AC118B(None)) shouldBe AC118(None)
      }

      "return correct value if at least one input is non empty" in new IntangibleAssetsCalculator {
        calculateAC118(AC118A(Some(1)), AC118B(None)) shouldBe AC118(Some(1))
        calculateAC118(AC118A(None), AC118B(Some(1))) shouldBe AC118(Some(1))
      }

      "return correct positive value" in new IntangibleAssetsCalculator {
        calculateAC118(AC118A(Some(2)), AC118B(Some(1))) shouldBe AC118(Some(3))
      }
    }

    "calculating AC119" when {
      "return None when all inputs are empty" in new IntangibleAssetsCalculator {
        calculateAC119(AC119A(None), AC119B(None)) shouldBe AC119(None)
      }

      "return correct value if at least one input is non empty" in new IntangibleAssetsCalculator {
        calculateAC119(AC119A(Some(1)), AC119B(None)) shouldBe AC119(Some(1))
        calculateAC119(AC119A(None), AC119B(Some(1))) shouldBe AC119(Some(1))
      }

      "return correct positive value" in new IntangibleAssetsCalculator {
        calculateAC119(AC119A(Some(2)), AC119B(Some(1))) shouldBe AC119(Some(3))
      }
    }

    "calculating AC120" when {
      "return None when all inputs are empty" in new IntangibleAssetsCalculator {
        calculateAC120(AC120A(None), AC120B(None)) shouldBe AC120(None)
      }

      "return correct value if at least one input is non empty" in new IntangibleAssetsCalculator {
        calculateAC120(AC120A(Some(1)), AC120B(None)) shouldBe AC120(Some(1))
        calculateAC120(AC120A(None), AC120B(Some(1))) shouldBe AC120(Some(1))
      }

      "return correct positive value" in new IntangibleAssetsCalculator {
        calculateAC120(AC120A(Some(2)), AC120B(Some(1))) shouldBe AC120(Some(3))
      }
    }

    "calculating AC211" when {
      "return None when all inputs are empty" in new IntangibleAssetsCalculator {
        calculateAC211(AC211A(None), AC211B(None)) shouldBe AC211(None)
      }

      "return correct value if at least one input is non empty" in new IntangibleAssetsCalculator {
        calculateAC211(AC211A(Some(1)), AC211B(None)) shouldBe AC211(Some(1))
        calculateAC211(AC211A(None), AC211B(Some(1))) shouldBe AC211(Some(1))
      }

      "return correct positive value" in new IntangibleAssetsCalculator {
        calculateAC211(AC211A(Some(2)), AC211B(Some(1))) shouldBe AC211(Some(3))
      }
    }

    "calculating AC121" when {
      "return None when all inputs are empty" in new IntangibleAssetsCalculator {
        calculateFullAC121(AC121A(None), AC121B(None)) shouldBe AC121(None)
      }

      "return correct value if at least one input is non empty" in new IntangibleAssetsCalculator {
        calculateFullAC121(AC121A(Some(1)), AC121B(None)) shouldBe AC121(Some(1))
        calculateFullAC121(AC121A(None), AC121B(Some(1))) shouldBe AC121(Some(1))
      }

      "return correct positive value" in new IntangibleAssetsCalculator {
        calculateFullAC121(AC121A(Some(2)), AC121B(Some(1))) shouldBe AC121(Some(3))
      }
    }

    "calculating AC122" when {
      "return None when all inputs are empty" in new IntangibleAssetsCalculator {
        calculateFullAC122(AC122A(None), AC122B(None)) shouldBe AC122(None)
      }

      "return correct value if at least one input is non empty" in new IntangibleAssetsCalculator {
        calculateFullAC122(AC122A(Some(1)), AC122B(None)) shouldBe AC122(Some(1))
        calculateFullAC122(AC122A(None), AC122B(Some(1))) shouldBe AC122(Some(1))
      }

      "return correct positive value" in new IntangibleAssetsCalculator {
        calculateFullAC122(AC122A(Some(2)), AC122B(Some(1))) shouldBe AC122(Some(3))
      }
    }

    "calculating AC123" when {
      "return None when all inputs are empty" in new IntangibleAssetsCalculator {
        calculateFullAC123(AC123A(None), AC123B(None)) shouldBe AC123(None)
      }

      "return correct value if at least one input is non empty" in new IntangibleAssetsCalculator {
        calculateFullAC123(AC123A(Some(1)), AC123B(None)) shouldBe AC123(Some(1))
        calculateFullAC123(AC123A(None), AC123B(Some(1))) shouldBe AC123(Some(1))
      }

      "return correct positive value" in new IntangibleAssetsCalculator {
        calculateFullAC123(AC123A(Some(2)), AC123B(Some(1))) shouldBe AC123(Some(3))
      }
    }
    
  }
}
