package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Input}

case class CP56(value: Int) extends CtBoxIdentifier(name = "Income from property") with CtInteger with Input
