package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtDate, Input}

case class CP2(value: LocalDate) extends CtBoxIdentifier(name = "End date of accounting period") with CtDate with Input