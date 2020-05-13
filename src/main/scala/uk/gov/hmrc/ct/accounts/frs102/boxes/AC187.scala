

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.box._

case class AC187(value: Option[Boolean]) extends CtBoxIdentifier(name = "Revaluation reserve note included") with CtOptionalBoolean with Input

object AC187 {

  def apply(value: Boolean): AC187 = AC187(Some(value))
}
