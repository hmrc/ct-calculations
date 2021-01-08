/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._

case class CP87(value: Int) extends CtBoxIdentifier(name = "Total first year allowance claimed") with CtInteger

object CP87 extends Linked[CP87Input, CP87] with CtTypeConverters {

  override def apply(cp87Input: CP87Input): CP87 = new CP87(cp87Input)
}
