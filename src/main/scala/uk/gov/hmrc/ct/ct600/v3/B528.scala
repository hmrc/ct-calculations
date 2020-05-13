

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

case class B528(value: Option[BigDecimal]) extends CtBoxIdentifier(name = "Self-assessment of tax payable") with CtOptionalBigDecimal

object B528 extends CorporationTaxCalculator with Calculated[B528, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B528 = {
    calculateSelfAssessmentOfTaxPayable(fieldValueRetriever.b525(), fieldValueRetriever.b527())
  }

}
