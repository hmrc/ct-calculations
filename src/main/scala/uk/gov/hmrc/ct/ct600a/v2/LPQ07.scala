package uk.gov.hmrc.ct.ct600a.v2

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalDate, Input}

case class LPQ07(value: Option[LocalDate]) extends CtBoxIdentifier(name = "When do you plan to file your return?") with CtOptionalDate with Input
