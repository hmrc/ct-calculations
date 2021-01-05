/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}

case class CP45(value: Int) extends CtBoxIdentifier(name = "Profit before tax") with CtInteger

object CP45 extends Linked[CP44, CP45] {

  override def apply(source: CP44): CP45 = CP45(source.value)
}
