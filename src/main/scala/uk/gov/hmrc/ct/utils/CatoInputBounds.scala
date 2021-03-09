/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.utils

import uk.gov.hmrc.ct.box.ValidatableBox._
object CatoInputBounds {

  val turnoverHMRCMaxValue632k: Int = 632000
  val turnoverCOHOMaxValue10m: Int = 10200000
  val minimumValue0: Int = 0
  val oldMaxValue99999999: Int = 99999999
  val oldMinValue99999999: Int = -99999999
  val minimumValueAsString: String = minimumValue0.toString
  val turnoverHMRCMaxWithCommas: String = commaForThousands(turnoverHMRCMaxValue632k)
  val turnoverCOHOMaxWithCommas: String = commaForThousands(turnoverCOHOMaxValue10m)
  val oldMaxWithCommas: String = commaForThousands(oldMaxValue99999999)
  val oldMinWithCommas: String = commaForThousands(oldMinValue99999999)
}
