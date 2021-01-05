/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}
import uk.gov.hmrc.ct.computations.CP252

case class B765(value: Int) extends CtBoxIdentifier("Designated environmentally friendly machinery and plant Capital allowances from the energy technology list") with CtInteger

object B765 extends Linked[CP252, B765] {

  override def apply(source: CP252): B765 = B765(source.orZero)
}
