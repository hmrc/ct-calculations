/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}


case class CP279(value: Option[Int]) extends CtBoxIdentifier("Trade Annual Investment Allowance") with CtOptionalInteger

object CP279 extends Linked[CP88, CP279]{

  override def apply(source: CP88): CP279 = CP279(source.value)
}
