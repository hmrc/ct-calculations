/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP96(value: Option[Int]) extends CtBoxIdentifier("Machinery and plant") with CtOptionalInteger

object CP96 extends Linked[CP91, CP96]{

  override def apply(source: CP91): CP96 = CP96(source.value)
}
