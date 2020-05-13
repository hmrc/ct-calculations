

package uk.gov.hmrc.ct

import scala.math.BigDecimal.RoundingMode

object RoundingFunctions {

  def roundUpToInt(bigDecimal: BigDecimal): Int = {
    bigDecimal.setScale(0, RoundingMode.UP).toInt
  }

  def roundDownToInt(bigDecimal: BigDecimal): Int = {
    bigDecimal.setScale(0, RoundingMode.DOWN).toInt
  }

}
