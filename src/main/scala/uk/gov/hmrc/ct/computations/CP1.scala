package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtDate, Input}

case class CP1(value: LocalDate) extends CtBoxIdentifier(name = "Start date of accounting period") with CtDate with Input


