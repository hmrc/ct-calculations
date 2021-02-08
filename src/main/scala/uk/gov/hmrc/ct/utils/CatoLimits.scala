/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.utils

import uk.gov.hmrc.ct.box.ValidatableBox._
object CatoLimits {

  val turnoverHMRCMaximumValue: Int = 632000
  val minimumValue: Int = 0
  val oldMaxValue: Int = 99999999
  val minimumValueAsString: String = 0.toString
  val turnoverHMRCMaximumWithCommas: String = commaForThousands(632000)
  val oldMaxWithCommas: String = commaForThousands(99999999)
  val oldMinWithCommas: String = commaForThousands(-99999999)
}
