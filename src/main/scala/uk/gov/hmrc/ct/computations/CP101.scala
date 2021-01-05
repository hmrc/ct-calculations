/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP101(value: Option[Int]) extends CtBoxIdentifier(name = "Depreciation") with CtOptionalInteger

object CP101 extends Linked[CP46, CP101] {

  override def apply(source: CP46): CP101 = CP101(source.value)
}
