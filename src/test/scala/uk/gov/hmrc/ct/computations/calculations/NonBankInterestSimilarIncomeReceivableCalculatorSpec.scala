package uk.gov.hmrc.ct.computations.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.{CP501, CP502}

class NonBankInterestSimilarIncomeReceivableCalculatorSpec extends WordSpec with Matchers {

  "NonBankInterestSimilarIncomeReceivableCalculator" should {
    "sum up the gross interest from property and the ancilliary income if both are defined" in new NonBankInterestSimilarIncomeReceivableCalculator {
      val grossInterest = CP501(Some(100))
      val ancilliaryIncome = CP502(Some(200))
      nonBankInterestSimilarIncomeReceivableCalculation(grossInterest, ancilliaryIncome) should be (Some(300))
    }
    "return None if the gross interest from property is not defined" in new NonBankInterestSimilarIncomeReceivableCalculator {
      val grossInterest = CP501(None)
      val ancilliaryIncome = CP502(Some(200))
      nonBankInterestSimilarIncomeReceivableCalculation(grossInterest, ancilliaryIncome) should be (None)
    }
    "return None if the ancilliary income is not defined" in new NonBankInterestSimilarIncomeReceivableCalculator {
      val grossInterest = CP501(Some(200))
      val ancilliaryIncome = CP502(None)
      nonBankInterestSimilarIncomeReceivableCalculation(grossInterest, ancilliaryIncome) should be (None)
    }
    "return None if both gross interest from property and ancilliary income are not defined" in new NonBankInterestSimilarIncomeReceivableCalculator{
      val grossInterest = CP501(None)
      val ancilliaryIncome = CP502(None)
      nonBankInterestSimilarIncomeReceivableCalculation(grossInterest, ancilliaryIncome) should be (None)
    }
  }

}
