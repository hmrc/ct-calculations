/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v3.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.ct600e.v3._

class IncomeCalculatorSpec extends WordSpec with Matchers with IncomeCalculator {

  "IncomeCalculator" should {
    "return None if all income boxes are None" in {
      calculateTotalIncome(e50 = E50(None), e55 = E55(None), e60 = E60(None), e65 = E65(None), e70 = E70(None), e75 = E75(None), e80 = E80(None), e85 = E85(None)) shouldBe E90(None)
    }

    "return sum of populated boxes if some income boxes have values" in {
      calculateTotalIncome(e50 = E50(Some(50)), e55 = E55(Some(55)), e60 = E60(None), e65 = E65(None), e70 = E70(None), e75 = E75(None), e80 = E80(None), e85 = E85(None)) shouldBe E90(Some(105))
    }

    "return sum of all boxes if all income boxes have values" in {
      calculateTotalIncome(e50 = E50(Some(50)), e55 = E55(Some(55)), e60 = E60(Some(60)), e65 = E65(Some(65)), e70 = E70(Some(70)), e75 = E75(Some(75)), e80 = E80(Some(80)), e85 = E85(Some(85))) shouldBe E90(Some(540))
    }

    "return sum of all boxes with the same values" in {
      calculateTotalIncome(e50 = E50(Some(50)), e55 = E55(Some(50)), e60 = E60(Some(60)), e65 = E65(Some(65)), e70 = E70(Some(70)), e75 = E75(Some(75)), e80 = E80(Some(80)), e85 = E85(Some(85))) shouldBe E90(Some(535))
    }
  }
}
