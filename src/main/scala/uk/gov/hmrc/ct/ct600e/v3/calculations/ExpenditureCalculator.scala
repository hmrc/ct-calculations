

package uk.gov.hmrc.ct.ct600e.v3.calculations

import uk.gov.hmrc.ct.ct600e.v3._

trait ExpenditureCalculator {

  def calculateTotalExpenditure(e95: E95, e100: E100, e105: E105, e110: E110, e115: E115, e120: E120): E125 = {
    val expenditureFields = Seq(e95, e100, e105, e110, e115, e120)
    if (expenditureFields.exists(_.value.nonEmpty)) {
      E125(Some(expenditureFields.map(_.orZero).sum))
    } else
      E125(None)
  }
}
