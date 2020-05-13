

package uk.gov.hmrc.ct.accounts.frs102.calculations

import uk.gov.hmrc.ct.accounts.frs102.boxes._
import uk.gov.hmrc.ct.box.CtTypeConverters

trait TotalFixedAssetsCalculator extends CtTypeConverters {

  def calculateCurrentTotalFixedAssets(ac42: AC42, ac44: AC44): AC48 = {
    (ac42.value, ac44.value) match {
      case (None, None) => AC48(None)
      case _ => AC48(Some(ac42 + ac44))
    }
  }

  def calculatePreviousTotalFixedAssets(ac43: AC43, ac45: AC45): AC49 = {
    (ac43.value, ac45.value) match {
      case (None, None) => AC49(None)
      case _ => AC49(Some(ac43 + ac45))
    }
  }

}
