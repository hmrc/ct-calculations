

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._

trait QualifyingExpenditureOnMachineryCalculation extends CtTypeConverters {

  def qualifyingExpenditureCalculation(cp82: CP82, cp83: CP83): CP253 = {
    CP253(cp82 + cp83)
  }
}
