/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v2.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.ct600e.v2._

class LoansAndDebtorsCalculatorSpec extends WordSpec with Matchers with LoansAndDebtorsCalculator {

  "LoansAndDebtorsCalculator" should {
    "return None if all income boxes are None" in {
      calculateFieldValue(E24eA(None), E24eB(None)) shouldBe E24e(None)
    }

    "return sum of populated boxes if some income boxes have values" in {
      calculateFieldValue(E24eA(None), E24eB(Some(31))) shouldBe E24e(Some(31))
    }

    "return sum of all boxes if all income boxes have values" in {
      calculateFieldValue(E24eA(Some(12)), E24eB(Some(10))) shouldBe E24e(Some(22))
    }
  }
}
