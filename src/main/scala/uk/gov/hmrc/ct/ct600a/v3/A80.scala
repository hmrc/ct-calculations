package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalBigDecimal}
import uk.gov.hmrc.ct.ct600.v3.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever


case class A80(value: Option[BigDecimal]) extends CtBoxIdentifier(name = "A80 - Tax payable under S419 ICTA 1988") with CtOptionalBigDecimal

object A80 extends Calculated[A80, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(fieldValueRetriever: CT600ABoxRetriever): A80 = {
    calculateA80(a20 = fieldValueRetriever.retrieveA20(),
      a45 = fieldValueRetriever.retrieveA45(),
      a70 = fieldValueRetriever.retrieveA70())
  }
}