package uk.gov.hmrc.ct.ct600.v2.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.calculations.QualifyingExpenditureOnMachineryCalculation
import uk.gov.hmrc.ct.computations.{CP253, CP81, CP82, CP87}

class QualifyingExpenditureOnMachineryCalculationSpec extends WordSpec with Matchers {

  "QualifyingExpenditureOnMachinery" should {

    "return 0 if CP82 is None" in new QualifyingExpenditureOnMachineryCalculation {
      qualifyingExpenditureCalculation(CP82(None), CP87(123), CP81(456)) shouldBe CP253(0)
    }

    "return 0 if CP82 is zero" in new QualifyingExpenditureOnMachineryCalculation {
      qualifyingExpenditureCalculation(CP82(Some(0)), CP87(123), CP81(456)) shouldBe CP253(0)
    }

    "return the calculated value if CP82 > 0" in new QualifyingExpenditureOnMachineryCalculation {
      qualifyingExpenditureCalculation(CP82(Some(100)), CP87(25), CP81(50)) shouldBe CP253(125)
    }
  }
}
