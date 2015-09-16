package uk.gov.hmrc.ct.ct600.calculations

import org.scalatest.{Matchers, WordSpec}

class Ct600AnnualConstantsSpec extends WordSpec with Matchers {

  "Ct600AnnualConstants" should {

    "fail if a year is requested below the minimum available" in {
      an[AssertionError] should be thrownBy Ct600AnnualConstants.constantsForTaxYear(TaxYear(2005))
    }

    "return constants for 2012" in {
      val data = Ct600AnnualConstants.constantsForTaxYear(TaxYear(2012))
      data.lowerRelevantAmount shouldBe BigDecimal("300000")
      data.upperRelevantAmount shouldBe BigDecimal("1500000")
      data.reliefFraction shouldBe BigDecimal("0.01")
      data.rateOfTax shouldBe BigDecimal("0.24")
    }

    "return constants for 2013" in {
      val data = Ct600AnnualConstants.constantsForTaxYear(TaxYear(2013))
      data.lowerRelevantAmount shouldBe BigDecimal("300000")
      data.upperRelevantAmount shouldBe BigDecimal("1500000")
      data.reliefFraction shouldBe BigDecimal("0.0075")
      data.rateOfTax shouldBe BigDecimal("0.23")
    }

    "return constants for 2014" in {
      val data = Ct600AnnualConstants.constantsForTaxYear(TaxYear(2014))
      data.lowerRelevantAmount shouldBe BigDecimal("300000")
      data.upperRelevantAmount shouldBe BigDecimal("1500000")
      data.reliefFraction shouldBe BigDecimal("0.0025")
      data.rateOfTax shouldBe BigDecimal("0.21")
    }

    "return constants for 2015" in {
      val data = Ct600AnnualConstants.constantsForTaxYear(TaxYear(2015))
      data.lowerRelevantAmount shouldBe BigDecimal("0")
      data.upperRelevantAmount shouldBe BigDecimal("0")
      data.reliefFraction shouldBe BigDecimal("0.00")
      data.rateOfTax shouldBe BigDecimal("0.20")
    }

    "exception when year greater than know years" in {
      an[java.lang.IllegalArgumentException] should be thrownBy {
        Ct600AnnualConstants.constantsForTaxYear(TaxYear(2999))
      }
    }
  }
}
