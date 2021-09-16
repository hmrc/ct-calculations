/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}
import uk.gov.hmrc.ct.computations.CP88

case class B690(value: Option[Int]) extends CtBoxIdentifier("Annual Investment Allowance") with CtOptionalInteger

object B690 extends Linked[CP88, B690] {
  override def apply(source: CP88): B690 = {
    val b690Value = source.value match {
      case Some(0) => None
      case sourceValue => sourceValue
    }
    B690(b690Value)
  }
}
