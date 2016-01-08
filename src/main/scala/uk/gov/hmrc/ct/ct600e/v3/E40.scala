package uk.gov.hmrc.ct.ct600e.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalDate, Input}

case class E40(value: Option[LocalDate]) extends CtBoxIdentifier("Claiming exemption date") with CtOptionalDate with Input
