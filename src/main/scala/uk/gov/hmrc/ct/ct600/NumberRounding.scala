

package uk.gov.hmrc.ct.ct600

trait NumberRounding {

  def roundedToIntHalfUp(value: BigDecimal): Int = {
    value.setScale(0, BigDecimal.RoundingMode.HALF_UP).toInt
  }

  def roundedToIntHalfDown(value: BigDecimal): Int = {
    value.setScale(0, BigDecimal.RoundingMode.HALF_DOWN).toInt
  }

  def roundedTwoDecimalPlaces(value: BigDecimal): BigDecimal = {
    value.setScale(2, BigDecimal.RoundingMode.HALF_UP)
  }

  def roundedToInt(value: BigDecimal): Int = {
    value.setScale(0, BigDecimal.RoundingMode.HALF_UP).toInt
  }

}
