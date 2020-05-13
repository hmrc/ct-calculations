

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.box._

case class AC7900(value: Option[Boolean]) extends CtBoxIdentifier(name = "Enter Post balance sheet events note?")
  with CtOptionalBoolean
  with Input
