/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations._

class IncomeFromPropertyCalculatorSpec extends WordSpec with Matchers  {

  "Income from Property calculator" should {
    "calculate net income" in new IncomeFromPropertyCalculator {
      netIncomeFromProperty(cp507 = CP507(0), cp508 = CP508(0)) shouldBe CP509(0)
    }
    "calculate negative net income" in new IncomeFromPropertyCalculator {
      netIncomeFromProperty(cp507 = CP507(0), cp508 = CP508(123)) shouldBe CP509(-123)
    }
    "calculate positive net income" in new IncomeFromPropertyCalculator {
      netIncomeFromProperty(cp507 = CP507(1000), cp508 = CP508(100)) shouldBe CP509(900)
    }
    "calculate total income" in new IncomeFromPropertyCalculator {
      totalIncomeFromProperty(CP509(0), CP510(Some(0))) shouldBe CP511(0)
    }
    "calculate negative total income" in new IncomeFromPropertyCalculator {
      totalIncomeFromProperty(CP509(-123), CP510(Some(123))) shouldBe CP511(0)
    }
    "calculate positive total income" in new IncomeFromPropertyCalculator {
      totalIncomeFromProperty(CP509(900), CP510(Some(300))) shouldBe CP511(1200)
    }
  }
}
