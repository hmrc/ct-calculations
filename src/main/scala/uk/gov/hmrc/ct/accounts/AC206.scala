package uk.gov.hmrc.ct.accounts

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtOptionalDate, CtBoxIdentifier, Input}

case class AC206(value: Option[LocalDate]) extends CtBoxIdentifier("Previous Period of Accounts End Date") with CtOptionalDate with Input

