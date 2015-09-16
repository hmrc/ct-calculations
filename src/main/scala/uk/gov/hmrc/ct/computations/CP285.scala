package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalDate, Input}

case class CP285(value: Option[LocalDate]) extends CtBoxIdentifier(name = "End date of accounting period from which trading loss is being brought back") with CtOptionalDate with Input
