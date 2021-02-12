/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.utils

import uk.gov.hmrc.ct.box.ValidatableBox._
object CatoLimits {

  val turnoverHMRCMaxValue: Int = 632000
  val turnoverCOHOMaxValue: Int = 10200000
  val minimumValue: Int = 0
  val oldMaxValue: Int = 99999999
  val minimumValueAsString: String = minimumValue.toString
  val turnoverHMRCMaxWithCommas: String = commaForThousands(turnoverHMRCMaxValue)
  val turnoverCOHOMaxWithCommas: String = commaForThousands(turnoverCOHOMaxValue)
  val oldMaxWithCommas: String = commaForThousands(oldMaxValue)
  val oldMinWithCommas: String = commaForThousands(-oldMaxValue)
}
