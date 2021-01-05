/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}


case class CP266(value: Int) extends CtBoxIdentifier("Total profits chargeable to CT (box 37)") with CtInteger

object CP266 extends Linked[CP295, CP266]{

  override def apply(source: CP295): CP266 = CP266(source.value)
}
