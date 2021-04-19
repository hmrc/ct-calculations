/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.CP670

// was B78
case class B700(value: Option[Int]) extends CtBoxIdentifier("Machinery and plant - special rate pool / Balancing charges") with CtOptionalInteger
object B700 extends Linked[CP670, B700] {
  override def apply(source: CP670): B700 = {
    val b700Value = source.value match {
      case Some(0) => None
      case sourceValue => sourceValue
    }
    B700(b700Value)
  }
}
