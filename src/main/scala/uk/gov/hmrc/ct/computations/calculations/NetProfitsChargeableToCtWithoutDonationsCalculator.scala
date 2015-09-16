package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.CATO13
import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations.{CP293, CP294}

trait NetProfitsChargeableToCtWithoutDonationsCalculator extends CtTypeConverters{
  def calculateNetProfitsChargeableToCtWithoutDonations(cp293: CP293, cp294: CP294): CATO13 = {
    val result = cp293 - cp294
    CATO13(result max 0)
  }
}
