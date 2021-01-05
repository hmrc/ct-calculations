/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP513(value: Option[Int]) extends CtBoxIdentifier with CtOptionalInteger

object CP513 extends Linked[CP502, CP513]{

  override def apply(source: CP502): CP513 = CP513(source.value)
}
