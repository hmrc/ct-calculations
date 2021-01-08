/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP246(value: Option[Int]) extends CtBoxIdentifier(name = "Allowances") with CtOptionalInteger

object CP246 extends Linked[CP93, CP246] {

  override def apply(source: CP93): CP246 = CP246(source.value)
}
