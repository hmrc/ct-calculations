/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP106(value: Option[Int]) extends CtBoxIdentifier(name = "Net loss on sale of fixed assets") with CtOptionalInteger

object CP106 extends Linked[CP51, CP106] {

  override def apply(source: CP51): CP106 = CP106(source.value)
}
