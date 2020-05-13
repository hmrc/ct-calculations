

package uk.gov.hmrc.ct.accounts

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalDate, Input}

case class AC205(value: Option[LocalDate]) extends CtBoxIdentifier("Previous Period of Accounts Start Date") with CtOptionalDate with Input
