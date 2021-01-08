/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}

case class CP39(value: Int) extends CtBoxIdentifier with CtInteger

object CP39 extends Linked[CP14, CP39] {

  override def apply(source: CP14): CP39 = CP39(source.value)
}
