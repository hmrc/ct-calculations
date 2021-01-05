/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP93(value: Option[Int]) extends CtBoxIdentifier("Machinery and plant") with CtOptionalInteger

object CP93 extends Linked[CP186, CP93]{

  override def apply(source: CP186): CP93 = CP93(source.value)
}
