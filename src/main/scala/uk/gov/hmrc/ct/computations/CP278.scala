/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}

case class CP278(value: Int) extends CtBoxIdentifier("Expenditure on designated environmentally friendly machinery and plant") with CtInteger

object CP278 extends Linked[CP252, CP278] {

  override def apply(source: CP252): CP278 = CP278(source.orZero)
}
