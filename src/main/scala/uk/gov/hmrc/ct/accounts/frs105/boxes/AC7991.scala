package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.box._

case class AC7991(value: Option[Boolean]) extends CtBoxIdentifier(name = "Commitments by way of guarantee")
  with CtOptionalBoolean
  with Input
