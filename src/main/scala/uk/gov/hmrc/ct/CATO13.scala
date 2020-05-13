

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger, NotInPdf}
import uk.gov.hmrc.ct.computations.calculations.NetProfitsChargeableToCtWithoutDonationsCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CATO13(value: Int) extends CtBoxIdentifier(name = "Net Profits Chargeable to CT without Charitable Donations") with CtInteger with NotInPdf

object CATO13 extends Calculated[CATO13, ComputationsBoxRetriever] with NetProfitsChargeableToCtWithoutDonationsCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CATO13 =
    calculateNetProfitsChargeableToCtWithoutDonations(
      fieldValueRetriever.cp293(), fieldValueRetriever.cp294(), fieldValueRetriever.chooseCp997())
}
