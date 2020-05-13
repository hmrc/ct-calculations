

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.box._

case class AC7400(value: Option[Boolean]) extends CtBoxIdentifier(name = "Enter Financial commitments note?")
  with CtOptionalBoolean
  with Input
