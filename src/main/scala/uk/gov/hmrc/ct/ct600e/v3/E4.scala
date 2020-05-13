

package uk.gov.hmrc.ct.ct600e.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, EndDate, Input}

case class E4(value: LocalDate) extends CtBoxIdentifier("Accounting Period end date") with EndDate with Input
