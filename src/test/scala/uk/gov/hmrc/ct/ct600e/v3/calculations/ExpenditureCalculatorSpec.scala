/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v3.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.ct600e.v3._

class ExpenditureCalculatorSpec extends WordSpec with ExpenditureCalculator with Matchers {

  "ExpenditureCalculator" should {
    "return None if all Expenditure boxes are None" in {
      calculateTotalExpenditure(e95 = E95(None), e100 = E100(None), e105 = E105(None), e110 = E110(None), e115 = E115(None), e120 = E120(None)) shouldBe E125(None)
    }

    "return sum of populated boxes if some Expenditure boxes have values" in {
      calculateTotalExpenditure(e95 = E95(Some(95)), e100 = E100(Some(100)), e105 = E105(Some(105)), e110 = E110(None), e115 = E115(None), e120 = E120(None)) shouldBe E125(Some(300))
    }

    "return sum of all boxes if all Expenditure boxes have values" in {
      calculateTotalExpenditure(e95 = E95(Some(95)), e100 = E100(Some(100)), e105 = E105(Some(105)), e110 = E110(Some(110)), e115 = E115(Some(115)), e120 = E120(Some(120))) shouldBe E125(Some(645))
    }

    "return sum of all boxes if some Expenditure boxes have the same value" in {
      calculateTotalExpenditure(e95 = E95(Some(95)), e100 = E100(Some(100)), e105 = E105(Some(95)), e110 = E110(Some(110)), e115 = E115(Some(115)), e120 = E120(Some(120))) shouldBe E125(Some(635))
    }
  }
}
