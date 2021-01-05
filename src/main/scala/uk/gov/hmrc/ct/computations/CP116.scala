/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}

case class CP116(value: Int) extends CtBoxIdentifier(name = "Total deductions") with CtInteger

object CP116 extends Linked[CP59, CP116] {

  override def apply(source: CP59): CP116 = CP116(source.value)
}
