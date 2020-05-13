/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.ct600.v3._

class B586CalculatorSpec extends WordSpec with Matchers {

  "B586" should{

    "be same as B345 for the single financial year" in new B586Calculator {
      {
        calculateB586(B360(Some(200)), B410(Some(0)), B330(2017), B380(None)) shouldBe B586(Some(200))
      }
    }

    "be same as total figure of (B345 + B395) for the two financial year" in new B586Calculator {
      {
        calculateB586(B360(Some(400)), B410(Some(400)), B330(2017), B380(Some(2018))) shouldBe B586(Some(800))
      }
    }
  }
}
