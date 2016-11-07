package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.box._

case class AC7992(value: Option[Boolean]) extends CtBoxIdentifier(name = "Advances and credits")
  with CtOptionalBoolean
  with Input
