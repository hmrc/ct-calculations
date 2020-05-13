/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.boxes._

class NetCurrentAssetsLiabilitiesCalculatorSpec extends WordSpec with Matchers with NetCurrentAssetsLiabilitiesCalculator {

  "NetCurrentAssetsLiabilitiesCalculator" should {

    "calculate Current Net Current Assets Liabilities" in {
      calculateCurrentNetCurrentAssetsLiabilities(AC56(Some(50)), AC138B(Some(20)), AC58(Some(30))) shouldBe AC60(Some(40))
    }

    "return None for Current Net Current Assets Liabilities when all inputs are None" in {
      calculateCurrentNetCurrentAssetsLiabilities(AC56(None), AC138B(None), AC58(None)) shouldBe AC60(None)
    }

    "calculate Previous Net Current Assets Liabilities" in {
      calculatePreviousNetCurrentAssetsLiabilities(AC57(Some(50)), AC139B(Some(20)), AC59(Some(30))) shouldBe AC61(Some(40))
    }

    "return None for Previous Net Current Assets Liabilities when all inputs are None" in {
      calculatePreviousNetCurrentAssetsLiabilities(AC57(None), AC139B(None), AC59(None)) shouldBe AC61(None)
    }

  }

}
