/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.calculations.QualifyingExpenditureOnMachineryCalculation
import uk.gov.hmrc.ct.computations._

class QualifyingExpenditureOnMachineryCalculationSpec extends WordSpec with Matchers {

  "QualifyingExpenditureOnMachinery" should {

    "add cp82 and cp83" in new QualifyingExpenditureOnMachineryCalculation {
      qualifyingExpenditureCalculation(CP82(3), CP83(4)) shouldBe CP253(7)
    }
  }
}
