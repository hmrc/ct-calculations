package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtOptionalBoolean, Input, CtBoxIdentifier}

case class CPQ1000(value: Option[Boolean]) extends CtBoxIdentifier(name = "Did you buy any company cars in this period?") with CtOptionalBoolean with Input{

}
