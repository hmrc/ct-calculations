/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}


case class CP274(value: Int) extends CtBoxIdentifier("Qualifying expenditure other machinery and plant") with CtInteger

object CP274 extends Linked[CP253, CP274]{

  override def apply(source: CP253): CP274 = CP274(source.value)
}
