/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations._

class TotalAdditionsCalculatorSpec extends WordSpec with Matchers {
  "Total Additions Calculator" should {
    "calculate additions with populated values" in new TotalAdditionsCalculator {
      totalAdditionsCalculation(cp46 = CP46(Some(2)),
                                cp47 = CP47(Some(3)),
                                cp48 = CP48(Some(4)),
                                cp49 = CP49(Some(5)),
                                cp51 = CP51(Some(6)),
                                cp52 = CP52(Some(7)),
                                cp53 = CP53(Some(8))) shouldBe CP54(35)
    }
  }
}
