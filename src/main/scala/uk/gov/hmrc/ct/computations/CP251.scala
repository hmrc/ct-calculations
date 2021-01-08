/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}


case class CP251(value: Int) extends CtBoxIdentifier("Expenditure on machinery and plant qualifying for first year allowance") with CtInteger

object CP251 extends Linked[CP81, CP251]{

  override def apply(source: CP81): CP251 = CP251(source.value)
}
