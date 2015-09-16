package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600.v3.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever

case class A30(value: Option[Int]) extends CtBoxIdentifier(name = "A30 - Amount repaid - sum of all iterations of amount of loan repayed (after period end but equal or less than 9 months from period end)") with CtOptionalInteger

object A30 extends Calculated[A30, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(fieldValueRetriever: CT600ABoxRetriever): A30 = {
    calculateA30(fieldValueRetriever.retrieveCP2(), fieldValueRetriever.retrieveA10())
  }
}