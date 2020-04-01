/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.accounts.AC401
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}

case class CP983(value: Int) extends CtBoxIdentifier(name = "Turnover from off-payroll working") with CtInteger

object CP983 extends Linked[AC401, CP983] {

  override def apply(source: AC401): CP983 = CP983(source.orZero)
}