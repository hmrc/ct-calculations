

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.box._

case class ACQ8999(value: Option[Boolean]) extends CtBoxIdentifier(name = "The company was dormant.")
  with CtOptionalBoolean
  with Input
