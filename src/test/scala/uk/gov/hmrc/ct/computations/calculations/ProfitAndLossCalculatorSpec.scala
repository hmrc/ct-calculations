/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs105.boxes.AC415
import uk.gov.hmrc.ct.computations._

class ProfitAndLossCalculatorSpec extends WordSpec with Matchers {

  "Calculating Profit Or Loss (CP14)" should {
    "return positive number when turnover is greater than costs" in new ProfitAndLossCalculator {
      calculateProfitOrLoss(CP7(Some(100)), CP8(Some(60)), CP981(None), CP983(None)) shouldBe CP14(40)
      calculateProfitOrLoss(CP7(Some(100)), CP8(Some(0)), CP981(None), CP983(None)) shouldBe CP14(100)
    }
    "return negative number when turnover is less than costs" in new ProfitAndLossCalculator {
      calculateProfitOrLoss(CP7(Some(100)), CP8(Some(107)), CP981(None), CP983(None)) shouldBe CP14(-7)
      calculateProfitOrLoss(CP7(Some(0)), CP8(Some(107)), CP981(None), CP983(None)) shouldBe CP14(-107)
    }
    "return zero if turnover is equal to costs" in new ProfitAndLossCalculator {
      calculateProfitOrLoss(CP7(Some(100)), CP8(Some(100)), CP981(None), CP983(None)) shouldBe CP14(0)
      calculateProfitOrLoss(CP7(Some(0)), CP8(Some(0)), CP981(None), CP983(None)) shouldBe CP14(0)
    }
    "include OPW boxes in calculation" in new ProfitAndLossCalculator {
      calculateProfitOrLoss(CP7(Some(100)), CP8(Some(60)), CP981(Some(20)), CP983(Some(120))) shouldBe CP14(140)
      calculateProfitOrLoss(CP7(Some(100)), CP8(Some(20)), CP981(Some(40)), CP983(Some(50))) shouldBe CP14(90)
    }
  }

  "Calculating Gross Profit Or Loss Before Tax (CP44)" should {
    "return a positive number when profit is greater than costs" in new ProfitAndLossCalculator {
      def calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp14: CP14 = CP14(100),
                                                            cp40: CP40 = CP40(50),
                                                            cp43: CP43 = CP43(Some(0)),
                                                            cp509: CP509 = CP509(0),
                                                            cp502: CP502 = CP502(Some(0))) = calculateGrossProfitOrLossBeforeTax(cp14, cp40, cp43, cp509, cp502)

      calculateGrossProfitOrLossBeforeTaxWithBaseParams() shouldBe CP44(50)
      calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp43 = CP43(Some(10))) shouldBe CP44(60)
      calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp509 = CP509(20)) shouldBe CP44(70)
      calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp502 = CP502(Some(30))) shouldBe CP44(80)
      calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp502 = CP502(Some(10)), cp509 = CP509(20)) shouldBe CP44(80)
    }

    "return a negative number when profit is less than costs" in new ProfitAndLossCalculator {
      def calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp14: CP14 = CP14(50),
                                                            cp40: CP40 = CP40(100),
                                                            cp43: CP43 = CP43(Some(0)),
                                                            cp509: CP509 = CP509(0),
                                                            cp502: CP502 = CP502(Some(0))) = calculateGrossProfitOrLossBeforeTax(cp14, cp40, cp43, cp509, cp502)


      calculateGrossProfitOrLossBeforeTaxWithBaseParams() shouldBe CP44(-50)
      calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp43 = CP43(Some(10))) shouldBe CP44(-40)
      calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp509 = CP509(20)) shouldBe CP44(-30)
      calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp502 = CP502(Some(30))) shouldBe CP44(-20)
      calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp502 = CP502(Some(10)), cp509 = CP509(20)) shouldBe CP44(-20)
    }

    "return zero when profit and costs are equal" in new ProfitAndLossCalculator {
      def calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp14: CP14 = CP14(100),
                                                            cp40: CP40 = CP40(100),
                                                            cp43: CP43 = CP43(Some(0)),
                                                            cp509: CP509 = CP509(0),
                                                            cp502: CP502 = CP502(Some(0))) = calculateGrossProfitOrLossBeforeTax(cp14, cp40, cp43, cp509, cp502)


      calculateGrossProfitOrLossBeforeTaxWithBaseParams() shouldBe CP44(0)
      calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp40 = CP40(110), cp43 = CP43(Some(10))) shouldBe CP44(0)
      calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp40 = CP40(120), cp509 = CP509(20)) shouldBe CP44(0)
      calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp40 = CP40(130), cp502 = CP502(Some(30))) shouldBe CP44(0)
      calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp40 = CP40(130), cp43 = CP43(Some(10)), cp509 = CP509(20)) shouldBe CP44(0)
    }
  }
}
